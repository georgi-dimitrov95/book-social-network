package com.bsn.api.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest(

        Long id,

        @NotNull(message = "Title is mandatory")
        @NotEmpty(message = "Title is mandatory")
        String title,

        @NotNull(message = "Author name is mandatory")
        @NotEmpty(message = "Author name is mandatory")
        String authorName,

        @NotNull(message = "ISBN is mandatory")
        @NotEmpty(message = "ISBN is mandatory")
        String isbn,

        @NotNull(message = "Synopsis is mandatory")
        @NotEmpty(message = "Synopsis is mandatory")
        @Size(min = 20, max = 100, message = "Should be between 20 and 100 characters long")
        String synopsis,

        boolean shareable
) {
}
