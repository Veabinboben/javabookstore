package com.example.bookstoreserver.presentation.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.bookstoreserver.presentation.models.ApiException;

@Service
public class FileUploadService {

    @Value("${app.file.upload-dir}")
    private String uploadDir;

    public String uploadMultipart(MultipartFile file) throws ApiException {
        try {
            InputStream is = file.getInputStream();
            String uniqueName = UUID.randomUUID().toString().replace("-", "") + ".jpg";
            BufferedImage inputFile = ImageIO.read(is);
            File outputFile = new File(String.format("%s%s", uploadDir, uniqueName));

            boolean success = ImageIO.write(inputFile, "jpeg", outputFile);
            if (!success) {
                throw new IOException();
            }
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            return baseUrl + "/uploads/" + uniqueName;
        } catch (IOException e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Upload failed");
        }
    }

}
