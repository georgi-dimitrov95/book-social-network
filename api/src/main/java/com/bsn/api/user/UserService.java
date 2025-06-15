package com.bsn.api.user;

import com.bsn.api.auth.RegisterResponse;
import com.bsn.api.book.Book;
import com.bsn.api.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BookRepository bookRepository;

    public List<RegisterResponse> findAllOwnersOfBookByTitle(String bookTitle) {
        List<Book> books = bookRepository.findAllByTitle(bookTitle);
        return books
                .stream()
                .map(Book::getOwner)
                .map(RegisterResponse::new)
                .toList();
    }
}
