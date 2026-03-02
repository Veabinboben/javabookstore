package com.example.bookstoreserver.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.GenresService;
import com.example.bookstoreserver.domain.services.PublishersService;
import com.example.bookstoreserver.presentation.models.ApiException;
import com.example.bookstoreserver.presentation.models.BookForm;
import com.example.bookstoreserver.presentation.models.BookForm;
import com.example.bookstoreserver.presentation.services.FileUploadService;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;
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
public class BooksContorller {

    @Autowired
    private BooksService bookService;
    @Autowired
    private AuthorsService authorsService;
    @Autowired
    private GenresService genresService;
    @Autowired
    private PublishersService publisherService;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/allBooks")
    public ResponseEntity<Page<Book>> getbooksPaged(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize, 
        @RequestParam(defaultValue = "") String titleFilter 
    ) {
        //System.out.println(bookService.books().get(0).title);
        return ResponseEntity.ok(bookService.allBooksPaginated(page,pageSize,titleFilter));
    }

    
    @GetMapping("/getBook")
    public ResponseEntity<Book> getBookById(@RequestParam long id) throws ApiException {
        try{
            return ResponseEntity.ok(bookService.getBookById(id));
        }
        catch (Exception e){
            throw new ApiException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    @PostMapping("/saveBook") 
    public ResponseEntity<Book> saveBook(@Value("${app.file.upload-dir}") String uploadDir, @ModelAttribute BookForm form) throws ApiException {
        String coverLink = null;
        if (form.getFile() != null) {
            coverLink = fileUploadService.uploadMultipart(form.getFile());
        }
        Book book = new Book();
        //If not null then it will try to update
        if (form.getId() != null){
            book.setId(form.getId()); 
        }
        try{
            book.setTitle(form.getTitle());
            book.setPublishDate(form.getPublishDate());
            book.setPrice(form.getPrice());
            book.setCoverLink(coverLink);
            for (long id : form.getAuthorIds()){
                book.addAuthor(authorsService.getAuthorById(id));
            }
            for (long id : form.getGenreIds()){
                book.addGenre(genresService.getGenreById(id));
            }
            for (long id : form.getPublisherIds()){
                book.addPublisher(publisherService.getPublisherById(id));
            }
        }
        catch (NullPointerException e){
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "Null value on not-null field");
        }
        bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @PostMapping("/deleteBook") 
    public ResponseEntity<Void> deleteBook(@RequestParam long id) {
        try{
            bookService.deleteBookById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Deletion failed");
        }
    }
}   
