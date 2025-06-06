package com.bsn.api.wishlist;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WishlistRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public boolean bookExistsInWishlist(Long userId, Long bookId) {
       String query = "SELECT COUNT(*) FROM wishlist WHERE user_id = :userId AND book_id = :bookId";
        Number count = (Number) entityManager.createNativeQuery(query)
                .setParameter("userId", userId)
                .setParameter("bookId", bookId)
                .getSingleResult();
        return count.intValue() > 0;
    }

    @Transactional
    public void addBookToWishlist(Long userId, Long bookId) {
        String query = "INSERT INTO wishlist (user_id, book_id) VALUES (:userId, :bookId)";
        entityManager.createNativeQuery(query)
            .setParameter("userId", userId)
            .setParameter("bookId", bookId)
            .executeUpdate();
    }

    @Transactional
    public void deleteBookFromWishlist(Long userId, Long bookId) {
        String query = "DELETE FROM wishlist WHERE user_id = :userId AND book_id = :bookId";
        entityManager.createNativeQuery(query)
                .setParameter("userId", userId)
                .setParameter("bookId", bookId)
                .executeUpdate();
    }
}
