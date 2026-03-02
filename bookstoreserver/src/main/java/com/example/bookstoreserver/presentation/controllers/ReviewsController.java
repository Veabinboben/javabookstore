package com.example.bookstoreserver.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.data.entities.Review;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.GenresService;
import com.example.bookstoreserver.domain.services.PublishersService;
import com.example.bookstoreserver.domain.services.ReviewsService;
import com.example.bookstoreserver.presentation.models.ApiException;
import com.example.bookstoreserver.presentation.models.forms.BookForm;
import com.example.bookstoreserver.presentation.models.forms.ReviewForm;
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
import java.util.NoSuchElementException;
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


@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private BooksService bookService;
    @Autowired
    private AuthorsService authorsService;
    @Autowired
    private ReviewsService reviewsService;


    @GetMapping("/all")
    public ResponseEntity<Page<Review>> getReviewsByBook(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int pageSize, 
        @RequestParam(defaultValue = "-1") int bookId 
    ) throws ApiException {
        Book book;
        try{
            book = bookService.getBookById(bookId);
        }
        catch (Exception e){
            throw new ApiException(HttpStatus.NOT_FOUND, "Invalid bookId");
        }
        return ResponseEntity.ok(reviewsService.getBookReviewsPaginated(page,pageSize,book));
    }

    @PostMapping("/save") 
    public ResponseEntity<Review> saveReview(@ModelAttribute ReviewForm form) throws ApiException {
        Book book;
        Author author;
        try{
            book = bookService.getBookById(form.getBookId());
        }
        catch (NoSuchElementException e){
            throw new ApiException(HttpStatus.NOT_FOUND, "No book found");
        }
        try{
            author = authorsService.getAuthorById(form.getAuthorId());
        }
        catch (NoSuchElementException e){
            throw new ApiException(HttpStatus.NOT_FOUND, "No author found");
        }

        Review review = new Review();
        try{
            review.setContents(form.getContents());
            review.setRating(form.getRating());
            review.setBook(book);
            review.setAuthor(author);
            reviewsService.saveReview(review);
            return ResponseEntity.ok(review);
        }
        catch (NullPointerException e){
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "Null value on not-null field");
        }
    }

}   
