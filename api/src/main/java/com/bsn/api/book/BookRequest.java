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

        @Size(min = 100, max = 500, message = "Synopsis should be between 100 and 500 characters long")
        String synopsis,

        String coverPath,

        boolean shareable
) {
}
