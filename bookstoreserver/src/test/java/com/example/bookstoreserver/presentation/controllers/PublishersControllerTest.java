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

import com.example.bookstoreserver.data.entities.Publisher;
import com.example.bookstoreserver.domain.services.PublishersService;

@WebMvcTest(PublishersController.class)
@AutoConfigureRestTestClient
public class PublishersControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private PublishersService publishersService;

    @Test
    void testAllRequest() {
        List<Publisher> expectedPage = List.of();

        when(publishersService.getPublishers(anyString()))
                .thenReturn(expectedPage);

        restTestClient.get().uri("/publishers/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody().equals(expectedPage);
    }

}
