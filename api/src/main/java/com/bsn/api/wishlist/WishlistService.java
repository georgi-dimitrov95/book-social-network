package com.bsn.api.wishlist;

import com.bsn.api.auth.AuthenticationService;
import com.bsn.api.book.Book;
import com.bsn.api.book.BookRepository;
import com.bsn.api.book.BookResponse;
import com.bsn.api.exception.BookNotFoundException;
import com.bsn.api.misc.PageResponse;
import com.bsn.api.user.User;
import com.bsn.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final AuthenticationService authenticationService;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final WishlistRepository wishlistRepository;

    public PageResponse<BookResponse> findWishlistedBooksOfUser (int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Book> wishlistedBooksPage = wishlistRepository.findWishlistedBooksByUserId(getCurrentUser().getId(), pageable);
        return convertPageToPageResponse(wishlistedBooksPage, BookResponse::new);
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
