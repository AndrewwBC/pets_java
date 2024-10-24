package com.example.pets4ever.storie.Processor;

import com.amazonaws.services.elastictranscoder.model.VideoParameters;
import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVAudioAttributes;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.IVVideoAttributes;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.VideoException;
import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class VideoProcessor {

    // Método para validar a duração do vídeo
    private boolean isValidDuration(MultipartFile inputFile) throws Exception {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile.getInputStream())) {
            grabber.start();
            double durationInSeconds = grabber.getLengthInTime() / 1_000_000.0;
            grabber.stop();
            return durationInSeconds <= 15.0;
        }
    }

    // Método para redimensionar o vídeo para 1080x1920 e salvar na raiz do projeto
    public File resizeVideo(MultipartFile inputFile) throws Exception {
        String projectRoot = System.getProperty("user.dir");
        String outputFileName = "resized_video.mp4";
        File outputFile = new File(projectRoot, outputFileName);

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile.getInputStream());
             FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1080, 1920, grabber.getAudioChannels())) {

            grabber.start();

            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            recorder.setFrameRate(grabber.getFrameRate());
            recorder.setSampleRate(grabber.getSampleRate());

            recorder.start();

            Frame frame;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }

            recorder.stop();
            grabber.stop();
        }

        System.out.println("Vídeo salvo em: " + outputFile.getAbsolutePath());
        return outputFile;
    }

    public MultipartFile resizeGit(MultipartFile file) throws IOException, VideoException {
        IVCompressor compressor = new IVCompressor();

        IVSize customRes = new IVSize();
        customRes.setWidth(1920);
        customRes.setHeight(1080);

        IVAudioAttributes audioAttribute = new IVAudioAttributes();
        audioAttribute.setBitRate(64000);
        audioAttribute.setChannels(2);
        audioAttribute.setSamplingRate(44100);

        IVVideoAttributes videoAttribute = new IVVideoAttributes();
        videoAttribute.setBitRate(2700000);
        videoAttribute.setFrameRate(30);
        videoAttribute.setSize(customRes);

        byte[] videoOutput = compressor.
                encodeVideoWithAttributes(file.getBytes(), VideoFormats.MP4, audioAttribute,videoAttribute);

        return new ByteArrayMultipartFile("file", "resized_video.mp4", videoOutput);
    }



}
