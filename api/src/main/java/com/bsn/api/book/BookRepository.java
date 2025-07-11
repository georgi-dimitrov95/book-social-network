package com.bsn.api.book;

import com.bsn.api.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findAllByAuthorName(String name);

    List<Book> findAllByTitle(String title);

    Page<Book> findByArchivedFalseAndShareableTrueAndOwnerIdNot(Long ownerId, Pageable pageable);

    Page<Book> findByOwnerId(Long ownerId, Pageable pageable);
}
