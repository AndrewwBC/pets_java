package com.example.pets4ever.infra.exceptions.post;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class HandlePostExceptions {
    @ExceptionHandler(WrongFileType.class)
    public ResponseEntity<?> handler(WrongFileType wrongFileType){
        return ResponseEntity.badRequest().body(wrongFileType.getMessage());
    }
    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<String> handle(AmazonClientException amazonClientException) {
        return ResponseEntity.badRequest().body(amazonClientException.getMessage());
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handle(IOException ioException) {
        return ResponseEntity.badRequest().body(ioException.getMessage());
    }

}
