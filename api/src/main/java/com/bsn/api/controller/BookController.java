package com.bsn.api.controller;

import com.bsn.api.model.Book;
import com.bsn.api.model.BookRequest;
import com.bsn.api.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookRequest bookRequest, Authentication connectedUser) {
        try {
            Book savedBook = bookService.save(bookRequest, connectedUser);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            String errorMessage = "Authenticated user not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id) {
        try {
            Book book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            String errorMessage = "Book not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
