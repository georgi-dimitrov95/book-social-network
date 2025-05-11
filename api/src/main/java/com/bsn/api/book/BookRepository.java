package com.bsn.api.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    Page<Book> findByArchivedFalseAndShareableTrueAndOwnerIdNot(Long ownerId, Pageable pageable);

    Page<Book> findByOwnerId(Long ownerId, Pageable pageable);
}
