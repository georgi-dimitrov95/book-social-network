package com.bsn.api.wishlist;

import com.bsn.api.book.BookResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WishlistEntryResponse {

    private BookResponse bookResponse;

    private String userFullName;

    private LocalDate dateAdded;
}
