package com.bsn.api.book_transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {

    Page<BookTransaction> findByBorrowerId(Long borrowerId, Pageable pageable);

    Page<BookTransaction> findByBookOwnerId(Long ownerId, Pageable pageable);

    boolean existsByBookIdAndReturnedFalse(Long bookId);

    Optional<BookTransaction> findByBookIdAndBorrowerIdAndReturnedFalse(Long bookId, Long borrowerId);

    Page<BookTransaction> findByBorrowerIdAndReturnedFalse(Long id, Pageable pageable);

    Page<BookTransaction> findByBorrowerIdAndReturnedTrue(Long id, Pageable pageable);
}
