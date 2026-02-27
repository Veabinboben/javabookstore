package com.example.bookstoreserver.presentation.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageViewController {
    
    //TODO remove later use as template
    //@GetMapping("/images/{filename:.+}")
    //Pathvariable
    @GetMapping("/image/{name}")
    public ResponseEntity<byte[]> getImage(@Value("${app.file.upload-dir}") String uploadDir, @PathVariable String name) throws IOException {
        // Load the image from the file system (adjust the path as needed)
        Path imagePath = Paths.get(String.format("%s%s.jpg", uploadDir, name)); // e.g., 
        byte[] imageBytes = Files.readAllBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        // Determine the media type dynamically based on file extension, for example
        String contentType = Files.probeContentType(imagePath);
        if (contentType == null) {
            contentType = MediaType.IMAGE_JPEG_VALUE; // Fallback to JPEG if type is unknown
        }
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

}
