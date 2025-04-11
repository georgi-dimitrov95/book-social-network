package com.bsn.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        Long id,
        @NotNull
        @NotEmpty
        String title,
        @NotNull
        @NotEmpty
        String isbn,
        @NotNull
        @NotEmpty
        String synopsis,
        boolean shareable
) {
}
