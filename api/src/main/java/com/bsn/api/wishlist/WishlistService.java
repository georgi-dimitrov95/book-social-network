package com.bsn.api.wishlist;

import com.bsn.api.auth.AuthenticationService;
import com.bsn.api.book.Book;
import com.bsn.api.book.BookRepository;
import com.bsn.api.book.BookResponse;
import com.bsn.api.exception.BookNotFoundException;
import com.bsn.api.misc.PageResponse;
import com.bsn.api.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final AuthenticationService authenticationService;

    private final BookRepository bookRepository;

    private final WishlistRepository wishlistRepository;

    public PageResponse<BookResponse> findWishlistedBooksOfUser (int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Book> wishlistedBooksPage = wishlistRepository.findWishlistedBooksByUserId(getCurrentUser().getId(), pageable);
        return convertPageToPageResponse(wishlistedBooksPage, BookResponse::new);
    }

    public WishlistEntryResponse addBookToWishlist(Long bookId) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        if (Objects.equals(user.getId(), book.getId())) {
            throw new AccessDeniedException("You can't wishlist your own book.");
        }

        if (wishlistRepository.existsByUserIdAndBookId(user.getId(), bookId)) {
            throw new AccessDeniedException("The book is already in your wishlist.");
        }

        WishlistEntry entry = new WishlistEntry(user, book, LocalDate.now());
        wishlistRepository.save(entry);
        BookResponse bookResponse = new BookResponse(book);
        return new WishlistEntryResponse(bookResponse, user.getFullName(), LocalDate.now());
    }

    public void removeBookFromWishlist(Long bookId) {
        User user = getCurrentUser();
        Book book = bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);

        WishlistEntry entry = wishlistRepository
                .findByUserIdAndBookId(getCurrentUser().getId(), book.getId())
                .orElseThrow(() -> new EntityNotFoundException("No such book was found in your wishlist"));

        wishlistRepository.delete(entry);
    }

    private <V, R> PageResponse<R> convertPageToPageResponse(Page<V> page, Function<V, R> mapper) {
        List<R> responses = page.getContent()
                .stream()
                .map(mapper)
                .toList();

        return new PageResponse<>(responses, page);
    }

    private User getCurrentUser() {
        return authenticationService.getAuthenticatedUser();
    }
}
