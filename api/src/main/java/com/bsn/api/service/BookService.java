package com.bsn.api.service;

import com.bsn.api.model.*;
import com.bsn.api.repository.BookRepository;
import com.bsn.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private UserRepository userRepository;

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

    private User getAuthenticatedUser(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
