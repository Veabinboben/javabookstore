package com.example.bookstoreserver.presentation.controllers;

import com.example.bookstoreserver.presentation.models.ApiErrorResponse;
import com.example.bookstoreserver.presentation.models.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlingControllerTest {

    @InjectMocks
    private ExceptionHandlingController exceptionHandlingController;

    @Mock
    private HttpServletRequest request;

    private static final String TEST_PATH = "/api/books/1";

    @BeforeEach
    void setUp() {
        when(request.getPathInfo()).thenReturn(TEST_PATH);
    }

    @Test
    void testHandleAll() {
        ApiException ex = new ApiException(HttpStatus.NOT_FOUND, "Book not found");

        ResponseEntity<ApiErrorResponse> response = exceptionHandlingController.handleAll(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);

    }

    @Test
    void testHandleAllFailure() {
        ApiException ex = new ApiException(HttpStatus.BAD_REQUEST, "Invalid input");

        ResponseEntity<ApiErrorResponse> response = exceptionHandlingController.handleAll(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getStatus()).isEqualTo(400);
    }

    @Test
    void testHandelvalidation() {
        ConstraintViolationException ex = new ConstraintViolationException("must not be null", Set.of());

        ResponseEntity<ApiErrorResponse> response = exceptionHandlingController.handleValidation(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(404);
    }

    @Test
    void testHandelvalidationNullPath() {
        when(request.getPathInfo()).thenReturn(null);
        ConstraintViolationException ex = new ConstraintViolationException("violation", Set.of());

        ResponseEntity<ApiErrorResponse> response = exceptionHandlingController.handleValidation(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}