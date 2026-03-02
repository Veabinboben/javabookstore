package com.example.bookstoreserver.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.presentation.models.ApiException;
import com.example.bookstoreserver.presentation.models.forms.AuthorForm;
import com.example.bookstoreserver.presentation.models.forms.BookForm;
import com.example.bookstoreserver.presentation.services.FileUploadService;

@RestController
@RequestMapping("/authors")
public class AuthorsController {
    
    @Autowired
    private AuthorsService authorsService;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAuthors(
        @RequestParam(defaultValue = "") String nameFilter 
    ) {
        //System.out.println(bookService.books().get(0).title);
        return ResponseEntity.ok(authorsService.getAuthors(nameFilter));
    }

    @PostMapping("/save") 
    public ResponseEntity<Author> saveAuthor(@Value("${app.file.upload-dir}") String uploadDir, @ModelAttribute AuthorForm form) throws ApiException {
        String photoLink = null;
        if (form.getFile() != null) {
            photoLink = fileUploadService.uploadMultipart(form.getFile());
        }
        Author author = new Author();
        try{
            author.setName(form.getName());
            author.setMiddleName(form.getMiddleName());
            author.setSurname(form.getSurname());
            author.setPhotoLink(photoLink);
            author.setBio(form.getBio());
            author.setBirthday(form.getBirthday());
        }
        catch (NullPointerException e){
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "Null value on not-null field");
        }
        authorsService.saveAuthor(author);;
        return ResponseEntity.ok(author);
    }
}
