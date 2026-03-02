package com.example.bookstoreserver.presentation.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.bookstoreserver.presentation.models.ApiErrorResponse;
import com.example.bookstoreserver.presentation.models.ApiException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    //TODO no instance in response fsr
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleAll(ApiException ex, HttpServletRequest req) {
        ApiErrorResponse body = new ApiErrorResponse(ex.geStatus().value(), ex.getMessage(), req.getPathInfo());
        return ResponseEntity.status(ex.geStatus()).body(body);
    }

}