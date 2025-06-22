package com.bsn.api.wishlist;

import com.bsn.api.book.BookResponse;
import com.bsn.api.misc.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/get/all")
    public ResponseEntity<PageResponse<WishlistEntryResponse>> getWishlistedBooksOfUser (
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size) {

        PageResponse<WishlistEntryResponse> pageResponse = wishlistService.findWishlistedBooksOfUser(page, size);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/is-wishlisted/{bookId}")
    public ResponseEntity<Boolean> isBookWishlistedByUser(@PathVariable Long bookId) {
        boolean wishlisted = wishlistService.isBookWishlistedByUser(bookId);
        return ResponseEntity.ok(wishlisted);
    }

    @PostMapping("/add/{bookId}")
    public ResponseEntity<WishlistEntryResponse> addBookToWishlist(@PathVariable Long bookId) {
        WishlistEntryResponse wishlistResponse = wishlistService.addBookToWishlist(bookId);
        return new ResponseEntity<>(wishlistResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Void> removeBookFromWishlist(@PathVariable Long bookId) {
        wishlistService.removeBookFromWishlist(bookId);
        return ResponseEntity.noContent().build();
    }
}
