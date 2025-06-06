package com.bsn.api.wishlist;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add/{bookId}")
    public ResponseEntity<Void> addBookToWishlist(@PathVariable Long bookId) {
        wishlistService.addBookToWishlist(bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
