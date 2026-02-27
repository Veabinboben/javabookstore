package com.example.bookstoreserver.presentation.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//TODO move vars to config maybe
@Service
public class FileUploadService {
    
    @Value("${app.file.upload-dir}") 
    private String uploadDir;

    public String uploadMultipart(MultipartFile file) throws IOException{
        InputStream is = file.getInputStream();
        String uniqueName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        BufferedImage inputFile = ImageIO.read(is); 
        File outputFile = new File(String.format("%s%s",uploadDir,uniqueName)); 

        boolean success = ImageIO.write(inputFile, "jpeg", outputFile);
        if (!success) {
           throw new IOException();
        }
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return baseUrl + "/uploads/" + uniqueName;
    }

}
