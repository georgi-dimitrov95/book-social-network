package com.bsn.api.controller;

import com.bsn.api.model.BookRequest;
import com.bsn.api.model.BookResponse;
import com.bsn.api.model.BorrowedBookResponse;
import com.bsn.api.model.PageResponse;
import com.bsn.api.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookRequest bookRequest, Authentication authentication) {
        try {
            BookResponse savedBook = bookService.save(bookRequest, authentication);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            String errorMessage = "Authenticated user not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> findBookById(@PathVariable Long id) {
        try {
            BookResponse book = bookService.findById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            String errorMessage = "Book not found with ID: " + id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksFromOtherOwners(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
        PageResponse<BookResponse> pageResponse = bookService.findALlBooksFromOtherOwners(page, size, authentication);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksOfCurrentUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
       PageResponse<BookResponse> pageResponse = bookService.findAllBooksOfCurrentUser(page, size, authentication);
       return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooksByCurrentUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllBorrowedBooksByCurrentUser(page, size, authentication);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/loaned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooksFromCurrentUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            Authentication authentication
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllBorrowedBooksFromCurrentUser(page, size, authentication);
        return ResponseEntity.ok(pageResponse);
    }

    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<?> updateBookShareableStatus(@RequestParam("bookId") Long bookId, Authentication authentication) {
        try {
            BookResponse book = bookService.updateBookShareableStatus(bookId, authentication);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            String errorMessage = "Book not found with ID: " + bookId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}
