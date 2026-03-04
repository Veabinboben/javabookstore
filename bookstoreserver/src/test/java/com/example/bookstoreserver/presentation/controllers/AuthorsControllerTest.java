package com.example.bookstoreserver.presentation.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.bookstoreserver.data.entities.Author;
import com.example.bookstoreserver.domain.services.AuthorsService;
import com.example.bookstoreserver.presentation.services.FileUploadService;

@WebMvcTest(AuthorsController.class)
@AutoConfigureRestTestClient
public class AuthorsControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private AuthorsService authorsService;
    @MockitoBean
    private FileUploadService fileUploadService;

    @Test
    void testAllRequest() {
        List<Author> expectedPage = List.of();

        when(authorsService.getAuthors(""))
                .thenReturn(expectedPage);

        restTestClient.get().uri("/authors/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

    @Test
    void testSaveRequest() {
        when(fileUploadService.uploadMultipart(any()))
                .thenReturn("");
        doNothing().when(authorsService).saveAuthor(any(Author.class));

        restTestClient.post()
                .uri("/authors/save")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("name=n&middleName=mn&surname=s&bio=b")
                .exchange()
                .expectStatus().isOk();
    }

}
