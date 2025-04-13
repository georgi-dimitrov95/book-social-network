package com.bsn.api.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FeedbackRequest(

        @Min(value = 0)
        @Max(value = 5)
        Double rating,

        @NotNull
        @NotEmpty
        @NotBlank
        String comment,

        @NotNull
        Long bookId
) {
}
