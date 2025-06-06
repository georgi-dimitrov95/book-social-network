package com.bsn.api.wishlist;

import com.bsn.api.auth.AuthenticationService;
import com.bsn.api.book.BookRepository;
import com.bsn.api.exception.BookNotFoundException;
import com.bsn.api.user.User;
import com.bsn.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final AuthenticationService authenticationService;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final WishlistRepository wishlistRepository;

    public void addBookToWishlist(Long bookId) {
        User currentUser = authenticationService.getAuthenticatedUser();

        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }

        if (wishlistRepository.bookExistsInWishlist(currentUser.getId(), bookId)) {
            throw new IllegalArgumentException("The book already exists in your wishlist");
        }

        wishlistRepository.addBookToWishlist(currentUser.getId(), bookId);
    }

    public void deleteBookFromWishlist(Long bookId) {
        User currentUser = authenticationService.getAuthenticatedUser();

        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }

        if (!wishlistRepository.bookExistsInWishlist(currentUser.getId(), bookId)) {
            throw new IllegalArgumentException("The book does not exist in your wishlist");
        }

        wishlistRepository.deleteBookFromWishlist(currentUser.getId(), bookId);
    }
}
