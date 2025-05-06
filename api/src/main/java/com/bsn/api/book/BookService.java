package com.bsn.api.book;

import com.bsn.api.auth.AuthenticationService;
import com.bsn.api.book_transaction.BookTransaction;
import com.bsn.api.exception.BookNotFoundException;
import com.bsn.api.file.FileStorageService;
import com.bsn.api.misc.*;
import com.bsn.api.book_transaction.BookTransactionRepository;
import com.bsn.api.user.UserRepository;
import com.bsn.api.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BookService {

    private AuthenticationService authenticationService;

    private BookRepository bookRepository;

    private UserRepository userRepository;

    private BookTransactionRepository bookTransactionRepository;

    private FileStorageService fileService;

    public Book save(BookRequest bookRequest) {
        User user = authenticationService.getAuthenticatedUser();
        Book book = new Book(bookRequest);
        book.setOwner(user);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    public PageResponse<BookResponse> findALlBooksFromOtherOwners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.findByArchivedFalseAndShareableTrueAndOwnerIdNot(authenticationService.getAuthenticatedUser().getId(), pageable);
        return convertPageToPageResponse(booksPage, BookResponse::new);
    }

    public PageResponse<BookResponse> findAllBooksOfCurrentUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.findByOwnerId(authenticationService.getAuthenticatedUser().getId(), pageable);
        return convertPageToPageResponse(booksPage, BookResponse::new);
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooksByCurrentUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookTransaction> bookTransactionsPage = bookTransactionRepository.findByBorrowerId(authenticationService.getAuthenticatedUser().getId(), pageable);
        return convertPageToPageResponse(bookTransactionsPage, BorrowedBookResponse::new);
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooksFromCurrentUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookTransaction> bookTransactionsPage = bookTransactionRepository.findByBookOwnerId(authenticationService.getAuthenticatedUser().getId(), pageable);
        return convertPageToPageResponse(bookTransactionsPage, BorrowedBookResponse::new);
    }

    public BookResponse updateBookShareableStatus(Long bookId) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);

        if (!Objects.equals(authenticationService.getAuthenticatedUser().getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't make changes to other users' books");
        }

        book.setShareable(!book.isShareable());
        return new BookResponse(bookRepository.save(book));
    }

    public BookResponse updateBookArchivedStatus(Long bookId) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);

        if (!Objects.equals(authenticationService.getAuthenticatedUser().getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't make changes to other users' books");
        }

        book.setArchived(!book.isArchived());
        return new BookResponse(bookRepository.save(book));
    }

    public BorrowedBookResponse borrowBook(Long bookId) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        User user = authenticationService.getAuthenticatedUser();

        if (book.isArchived() || !book.isShareable()) {
            throw new AccessDeniedException("You can't borrow a book that is archived or is not shareable");
        }

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't borrow your own book");
        }

        boolean alreadyBorrowed = bookTransactionRepository.existsByBookIdAndReturnedFalse(book.getId());
        if (alreadyBorrowed) {
            throw new AccessDeniedException("The requested book is already borrowed");
        }

        BookTransaction bookTransaction = new BookTransaction();
        bookTransaction.setBorrower(user);
        bookTransaction.setBook(book);
        bookTransaction.setReturned(false);
        bookTransaction.setBorrowedAt(new Date());

        return new BorrowedBookResponse(bookTransactionRepository.save(bookTransaction));
    }

    public BorrowedBookResponse returnBook(Long bookId) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        User user = authenticationService.getAuthenticatedUser();

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't borrow or return your own book");
        }

        BookTransaction bookTransaction = bookTransactionRepository.findByBookIdAndBorrowerIdAndReturnedFalse(
                book.getId(), user.getId()).orElseThrow(() -> new AccessDeniedException("You haven't borrowed this book or you have but you already returned it"));

        bookTransaction.setReturned(true);
        bookTransaction.setReturnedAt(new Date());

        return new BorrowedBookResponse(bookTransactionRepository.save(bookTransaction));
    }

    public void uploadBookCoverPicture(MultipartFile file, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        var bookPicturePath = fileService.saveFile(file, authenticationService.getAuthenticatedUser().getId());
        book.setBookCoverPath(bookPicturePath);
        bookRepository.save(book);
    }

    private <V, R> PageResponse<R> convertPageToPageResponse(Page<V> page, Function<V, R> mapper) {
        List<R> responses = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PageResponse<>(responses, page);
    }
}
