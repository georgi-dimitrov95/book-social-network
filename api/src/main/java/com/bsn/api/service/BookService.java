package com.bsn.api.service;

import com.bsn.api.model.Book;
import com.bsn.api.model.BookRequest;
import com.bsn.api.model.User;
import com.bsn.api.repository.BookRepository;
import com.bsn.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private BookRepository bookRepository;

    private UserRepository userRepository;

    public Book save(BookRequest bookRequest, Authentication connectedUser) {
        String userEmail = (String) connectedUser.getPrincipal();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Book book = new Book(bookRequest);
        book.setOwner(user);
        return bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
