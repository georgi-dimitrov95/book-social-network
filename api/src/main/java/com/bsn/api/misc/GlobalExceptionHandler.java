package com.bsn.api.misc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException e) {
        Set<String> errors = new HashSet<>();
        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        ExceptionResponse response = new ExceptionResponse();
        response.setValidationErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
