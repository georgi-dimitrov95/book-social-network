package com.bsn.api.controller;

import com.bsn.api.model.Book;
import com.bsn.api.model.BookRequest;
import com.bsn.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
