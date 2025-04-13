package com.bsn.api.repository;

import com.bsn.api.model.BookResponse;
import com.bsn.api.model.BookTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {

    Page<BookTransaction> findByBorrowerId(Long borrowerId, Pageable pageable);

    Page<BookTransaction> findByBookOwnerId(Long ownerId, Pageable pageable);
}
