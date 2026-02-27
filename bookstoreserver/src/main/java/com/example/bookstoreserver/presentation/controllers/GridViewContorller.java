package com.example.bookstoreserver.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.presentation.models.BookCreateForm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    

    @PostMapping("/createBook") 
    public String createBook(@Value("${app.file.upload-dir}") String uploadDir, @ModelAttribute BookCreateForm form) throws IOException {
        //StringBuilder fileNames = new StringBuilder();
        //TODO change to local dir, probably from config
        MultipartFile file = form.getFile();
        InputStream is = file.getInputStream();
        String uniqueName = UUID.randomUUID().toString().replaceAll("-", "");
        BufferedImage inputFile = ImageIO.read(is); 
        File outputFile = new File(String.format("%s%s.jpg",uploadDir,uniqueName)); 

        boolean success = ImageIO.write(inputFile, "jpeg", outputFile);

        //Path fileNameAndPath = Paths.get("D:\\Projects\\Java\\javabookstore\\upload", uniqueName);
        //fileNames.append(file.getOriginalFilename());
        //Files.write(fileNameAndPath, file.getBytes());
        //model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        return "asd";
    }
}   
