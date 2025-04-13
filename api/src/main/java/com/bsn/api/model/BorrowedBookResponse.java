package com.bsn.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookResponse extends BookResponse{

    private String borrowedFrom;

    private boolean returned;

    private Date borrowedAt;

    private Date returnedAt;

    public BorrowedBookResponse(BookTransaction bookTransaction) {
        Book book = bookTransaction.getBook();
        super(book);
        this.borrowedFrom = book.getOwner().getFullName();
        this.returned = bookTransaction.isReturned();
        this.borrowedAt = bookTransaction.getBorrowedAt();
        this.returnedAt = bookTransaction.getReturnedAt();
    }
}
