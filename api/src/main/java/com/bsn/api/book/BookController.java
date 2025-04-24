package com.bsn.api.book;

import com.bsn.api.misc.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookResponse> saveBook(@RequestBody @Valid BookRequest bookRequest, Authentication authentication) {
        Book savedBook = bookService.save(bookRequest, authentication);
        BookResponse bookResponse = new BookResponse(savedBook);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
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

    @PatchMapping("/archived/{bookId}")
    public ResponseEntity<?> updateBookArchivedStatus(@RequestParam("bookId") Long bookId, Authentication authentication) {
        try {
            BookResponse book = bookService.updateBookArchivedStatus(bookId, authentication);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            String errorMessage = "Book not found with ID: " + bookId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<?> borrowBook(@RequestParam("bookId") Long bookId, Authentication authentication) {
        try {
            BorrowedBookResponse borrowedBook = bookService.borrowBook(bookId, authentication);
            return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            String errorMessage = "Book not found with ID: " + bookId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<?> returnBook(@RequestParam("bookId") Long bookId, Authentication authentication) {
        try {
            BorrowedBookResponse returnedBook = bookService.returnBook(bookId, authentication);
            return new ResponseEntity<>(returnedBook, HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            String errorMessage = "Book not found with ID: " + bookId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping(value = "/cover/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("bookId") Long bookId,
            @Parameter()
            @RequestPart("file")MultipartFile file,
            Authentication authentication
    ) {
        bookService.uploadBookCoverPicture(file, bookId, authentication);
        return ResponseEntity.accepted().build();
    }
}
