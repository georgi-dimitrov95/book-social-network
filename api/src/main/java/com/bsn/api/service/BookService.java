package com.bsn.api.service;

import com.bsn.api.model.Book;
import com.bsn.api.model.BookRequest;
import com.bsn.api.model.BookResponse;
import com.bsn.api.model.User;
import com.bsn.api.repository.BookRepository;
import com.bsn.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private UserRepository userRepository;

    public BookResponse save(BookRequest bookRequest, Authentication connectedUser) {
        String userEmail = (String) connectedUser.getPrincipal();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Book book = new Book(bookRequest);
        book.setOwner(user);
        Book savedBook = bookRepository.save(book);
        return new BookResponse(savedBook);
    }

    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new BookResponse(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
