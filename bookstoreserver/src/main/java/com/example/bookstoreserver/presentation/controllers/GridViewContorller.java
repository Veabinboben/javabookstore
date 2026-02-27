package com.example.bookstoreserver.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.BooksService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//TODO disable whitelabel error
//TODO add file upload support

@RestController
@RequestMapping("/main")
public class GridViewContorller {

    @Autowired
    private BooksService bookService;

    @GetMapping("/all")
    public ResponseEntity<Page<Book>> booksPaged(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize, 
        @RequestParam(defaultValue = "") String titleFilter 
    ) {
        //System.out.println(bookService.books().get(0).title);
        return ResponseEntity.ok(bookService.allBooksPaginated(page,pageSize,titleFilter));
    }

    //TODO remove later use as template
    //@GetMapping("/images/{filename:.+}")
    //Pathvariable
    @GetMapping("/images")
    public ResponseEntity<byte[]> getImage() throws IOException {
        // Load the image from the file system (adjust the path as needed)
        Path imagePath = Paths.get("D:\\рундум portable\\BOAR.jpg"); // e.g., "C:/images/"
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

    @PostMapping("/upload") public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        //TODO change to local dir, probably from config
        Path fileNameAndPath = Paths.get("D:\\Projects\\Java\\javabookstore\\upload", file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "imageupload/index";
    }
}   
