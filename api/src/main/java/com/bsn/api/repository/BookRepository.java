package com.bsn.api.repository;

import com.bsn.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    public Optional<Book> findByIsbn(String isbn);
}
