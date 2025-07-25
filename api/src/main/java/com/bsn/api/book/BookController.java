package com.bsn.api.book;

import com.bsn.api.misc.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLOutput;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooksByAuthor(@RequestParam String authorName) {
        List<BookResponse> response = bookService.findAllBooksOfAuthor(authorName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksOfUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
       PageResponse<BookResponse> pageResponse = bookService.findAllBooksOfCurrentUser(page, size);
       return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/borrowed-all")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooksByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllBorrowedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/borrowed-currently")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllCurrentlyBorrowedBooksByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllCurrentlyBorrowedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/borrowed-returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooksByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllReturnedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/loaned-all")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllLoanedBooksByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllLoanedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/loaned-currently")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllCurrentlyLoanedBooksByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllCurrentlyLoanedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/loaned-returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllLoanedAndReturnedBooksByUser(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size
    ) {
        PageResponse<BorrowedBookResponse> pageResponse = bookService.findAllLoanedAndReturnedBooksByUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @PatchMapping("/shareable/{bookId}")
    public ResponseEntity<BookResponse> updateBookShareableStatus(@PathVariable Long bookId) {
        BookResponse bookResponse = bookService.updateBookShareableStatus(bookId);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PatchMapping("/archived/{bookId}")
    public ResponseEntity<BookResponse> updateBookArchivedStatus(@PathVariable Long bookId) {
        BookResponse bookResponse = bookService.updateBookArchivedStatus(bookId);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<BorrowedBookResponse> borrowBook(@PathVariable Long bookId) {
        BorrowedBookResponse borrowedBook = bookService.borrowBook(bookId);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

    @PostMapping("/borrow-from-user")
    public ResponseEntity<BorrowedBookResponse> borrowBookByTitleFromUser(
            @RequestParam String title,
            @RequestParam Long userId) {
        BorrowedBookResponse borrowedBook = bookService.borrowBookByTitleFromUser(title, userId);
        return new ResponseEntity<>(borrowedBook, HttpStatus.OK);
    }

    @PatchMapping("/return/{bookId}")
    public ResponseEntity<BorrowedBookResponse> returnBook(@PathVariable Long bookId) {
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
