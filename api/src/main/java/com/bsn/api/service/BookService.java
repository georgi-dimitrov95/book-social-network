package com.bsn.api.service;

import com.bsn.api.model.*;
import com.bsn.api.repository.BookRepository;
import com.bsn.api.repository.BookTransactionRepository;
import com.bsn.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private UserRepository userRepository;

    private BookTransactionRepository bookTransactionRepository;

    public BookResponse save(BookRequest bookRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Book book = new Book(bookRequest);
        book.setOwner(user);
        Book savedBook = bookRepository.save(book);
        return new BookResponse(savedBook);
    }

    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new BookResponse(book);
    }

    public PageResponse<BookResponse> findALlBooksFromOtherOwners(int page, int size, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.findByArchivedFalseAndShareableTrueAndOwnerIdNot(user.getId(), pageable);
        List<BookResponse> bookResponses = booksPage.getContent()
                .stream()
                .map(BookResponse::new)
                .toList();

        return new PageResponse<>(bookResponses, booksPage);
    }

    public PageResponse<BookResponse> findAllBooksOfCurrentUser(int page, int size, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> booksPage = bookRepository.findByOwnerId(user.getId(), pageable);
        List<BookResponse> bookResponses = booksPage.getContent()
                .stream()
                .map(BookResponse::new)
                .toList();

        return new PageResponse<>(bookResponses, booksPage);
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooksByCurrentUser(int page, int size, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<BookTransaction> bookTransactionsPage = bookTransactionRepository.findByBorrowerId(user.getId(), pageable);
        List<BorrowedBookResponse> borrowedBookResponses = bookTransactionsPage.getContent()
                .stream()
                .map(BorrowedBookResponse::new)
                .toList();

        return new PageResponse<>(borrowedBookResponses, bookTransactionsPage);
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooksFromCurrentUser(int page, int size, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<BookTransaction> bookTransactionsPage = bookTransactionRepository.findByBookOwnerId(user.getId(), pageable);
        List<BorrowedBookResponse> borrowedBookResponses = bookTransactionsPage.getContent()
                .stream()
                .map(BorrowedBookResponse::new)
                .toList();

        return new PageResponse<>(borrowedBookResponses, bookTransactionsPage);
    }

    public BookResponse updateBookShareableStatus(Long bookId, Authentication authentication) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        User user = getAuthenticatedUser(authentication);

        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't make changes to other users' books");
        }

        book.setShareable(!book.isShareable());
        return new BookResponse(bookRepository.save(book));
    }

    public BookResponse updateBookArchivedStatus(Long bookId, Authentication authentication) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        User user = getAuthenticatedUser(authentication);

        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't make changes to other users' books");
        }

        book.setArchived(!book.isArchived());
        return new BookResponse(bookRepository.save(book));
    }

    public BorrowedBookResponse borrowBook(Long bookId, Authentication authentication) throws AccessDeniedException {
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        User user = getAuthenticatedUser(authentication);

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

    private User getAuthenticatedUser(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
