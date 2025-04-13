package com.bsn.api.repository;

import com.bsn.api.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByArchivedFalseAndShareableTrueAndOwnerIdNot(Long ownerId, Pageable pageable);

    Page<Book> findByOwnerId(Long ownerId, Pageable pageable);
}
