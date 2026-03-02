package com.example.bookstoreserver.presentation.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import com.example.bookstoreserver.data.entities.Genre;
import com.example.bookstoreserver.domain.services.GenresService;

@WebMvcTest(GenresController.class)
@AutoConfigureRestTestClient
public class GenresControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private GenresService genresService;

    @Test
    void testAllRequest() {
        List<Genre> expectedPage = List.of();

        when(genresService.getGenres(anyString()))
                .thenReturn(expectedPage);

        restTestClient.get().uri("/genres/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

}
