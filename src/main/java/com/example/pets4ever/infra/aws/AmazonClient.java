package com.example.pets4ever.infra.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.annotation.PostConstruct;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {
    private AmazonS3 s3client;

    @Value("${S3_ENDPOINT}")
    private String endpointUrl;

    @Value("${S3_BUCKET_NAME}")
    private String bucketName;

    @Value("${S3_ACCESS_KEY}")
    private String accessKey;

    @Value("${S3_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile) {
        try {
            System.out.println(multipartFile.getOriginalFilename());
            File file = convertMultiPartToFile(multipartFile);

            File input = new File(file.getName());
            File output = new File(file.getName());

            Thumbnails.of(input)
                    .scale(1)
                    .outputQuality(0.5)
                    .toFile(output);

            uploadFileTos3bucket(file.getName(), file);

            System.out.println(file.getName());

        } catch (Exception e) {
            e.getMessage();
        }
        return "Uploaded!";
    }

    public String deleteFileFromS3Bucket(String fileName) {
        System.out.println(this.bucketName + "/" + fileName);
        this.s3client.deleteObject(new DeleteObjectRequest(this.bucketName, fileName));
        return "Successfully deleted";
    }




}