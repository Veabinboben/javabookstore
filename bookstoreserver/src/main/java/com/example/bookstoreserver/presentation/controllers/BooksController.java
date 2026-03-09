package com.example.bookstoreserver.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.bookstoreserver.data.entities.Book;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.domain.services.BooksService;
import com.example.bookstoreserver.domain.services.GenresService;
import com.example.bookstoreserver.domain.services.PublishersService;
import com.example.bookstoreserver.presentation.models.ApiException;
import com.example.bookstoreserver.presentation.models.forms.BookForm;
import com.example.bookstoreserver.presentation.services.FileUploadService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService bookService;
    private final AuthorsService authorsService;
    private final GenresService genresService;
    private final PublishersService publisherService;
    private final FileUploadService fileUploadService;

    public BooksController(BooksService bookService, AuthorsService authorsService, GenresService genresService,
            PublishersService publisherService, FileUploadService fileUploadService) {
        this.bookService = bookService;
        this.authorsService = authorsService;
        this.genresService = genresService;
        this.publisherService = publisherService;
        this.fileUploadService = fileUploadService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Book>> getbooksPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "") String titleFilter) {
        return ResponseEntity.ok(bookService.getBooksPaginated(page, pageSize, titleFilter));
    }

    @GetMapping("/getById")
    public ResponseEntity<Book> getBookById(@RequestParam long id) throws ApiException {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (Exception e) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Book not found");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@Value("${app.file.upload-dir}") String uploadDir,
            @ModelAttribute BookForm form) throws ApiException {
        String coverLink = null;
        if (form.getFile() != null) {
            coverLink = fileUploadService.uploadMultipart(form.getFile());
        }
        Book book = new Book();
        if (form.getId() != null) {
            book.setId(form.getId());
        }
        try {
            book.setTitle(form.getTitle());
            book.setPublishDate(form.getPublishDate());
            book.setPrice(form.getPrice());
            book.setCoverLink(coverLink);
            for (long id : form.getAuthorIds()) {
                book.addAuthor(authorsService.getAuthorById(id));
            }
            for (long id : form.getGenreIds()) {
                book.addGenre(genresService.getGenreById(id));
            }
            for (long id : form.getPublisherIds()) {
                book.addPublisher(publisherService.getPublisherById(id));
            }
        } catch (NullPointerException e) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE, "Null value on not-null field");
        }
        bookService.saveBook(book);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam long id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Deletion failed");
        }
    }
}
