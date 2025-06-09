package com.bsn.api.wishlist;

import com.bsn.api.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntry, Long> {

    @Query("SELECT w.book FROM WishlistEntry w WHERE w.user.id = :userId")
    Page<Book> findWishlistedBooksByUserId(@Param("userId") Long userId, Pageable pageable);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);
}
