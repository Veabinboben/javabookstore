package com.example.bookstoreserver.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.bookstoreserver.presentation.models.ApiErrorResponse;
import com.example.bookstoreserver.presentation.models.ApiException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleAll(ApiException ex, HttpServletRequest req) {
        ApiErrorResponse body = new ApiErrorResponse(ex.geStatus().value(), ex.getMessage(), req.getPathInfo());
        return ResponseEntity.status(ex.geStatus()).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(ConstraintViolationException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponse body = new ApiErrorResponse(status.value(), ex.getMessage(), req.getPathInfo());
        return ResponseEntity.status(status).body(body);
    }

}