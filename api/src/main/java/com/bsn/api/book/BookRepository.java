package com.bsn.api.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    @Query("""
    SELECT b FROM Book b
    WHERE b.id IN (
        SELECT MIN(b2.id) FROM Book b2
        WHERE b2.authorName = :name
        GROUP BY b2.title
        )
    """)
    List<Book> findDistinctBooksByTitleFromAuthor(@Param("name") String name);

    Optional<Book> findFirstByArchivedFalseAndShareableTrueAndTitleAndOwnerId(String title, Long ownerId);

    List<Book> findAllByTitle(String title);

    Page<Book> findByArchivedFalseAndShareableTrueAndOwnerIdNot(Long ownerId, Pageable pageable);

    Page<Book> findByOwnerId(Long ownerId, Pageable pageable);
}
