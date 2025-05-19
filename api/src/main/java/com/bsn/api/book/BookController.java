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
    public ResponseEntity<BookResponse> saveBook(@RequestBody @Valid BookRequest bookRequest) {
        Book savedBook = bookService.save(bookRequest);
        BookResponse bookResponse = new BookResponse(savedBook);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id) {
        Book foundBook = bookService.findById(id);
        BookResponse bookResponse = new BookResponse(foundBook);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksFromOtherOwners(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BookResponse> pageResponse = bookService.findALlBooksFromOtherOwners(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksOfCurrentUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
       PageResponse<BookResponse> pageResponse = bookService.findAllBooksOfCurrentUser(page, size);
       return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooksByCurrentUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllCurrentlyBorrowedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/loaned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooksFromCurrentUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllBorrowedBooksFromCurrentUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<BookResponse> updateBookShareableStatus(@RequestParam("bookId") Long bookId) {
        BookResponse bookResponse = bookService.updateBookShareableStatus(bookId);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PatchMapping("/archived/{bookId}")
    public ResponseEntity<BookResponse> updateBookArchivedStatus(@RequestParam("bookId") Long bookId) {
        BookResponse bookResponse = bookService.updateBookArchivedStatus(bookId);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<BorrowedBookResponse> borrowBook(@RequestParam("bookId") Long bookId) {
        BorrowedBookResponse borrowedBook = bookService.borrowBook(bookId);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<BorrowedBookResponse> returnBook(@RequestParam("bookId") Long bookId) {
        BorrowedBookResponse returnedBook = bookService.returnBook(bookId);
        return new ResponseEntity<>(returnedBook, HttpStatus.OK);
    }

    @PostMapping(value = "/cover/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("bookId") Long bookId,
            @Parameter()
            @RequestPart("file")MultipartFile file
    ) {
        bookService.uploadBookCoverPicture(file, bookId);
        return ResponseEntity.accepted().build();
    }
}
