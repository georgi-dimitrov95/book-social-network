package com.bsn.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createExceptionResponse(e));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createExceptionResponse(e));
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ExceptionResponse> handleInternalException(InternalException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createExceptionResponse(e));
    }

    @ExceptionHandler({ UsernameNotFoundException.class, EntityNotFoundException.class })
    public ResponseEntity<ExceptionResponse> handleNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createExceptionResponse(e));
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBookNotFoundException(BookNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setError("No book found with such an ID");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(createExceptionResponse(e));
    }

    private ExceptionResponse createExceptionResponse(Exception e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setError(e.getMessage());
        return response;
    }
}
