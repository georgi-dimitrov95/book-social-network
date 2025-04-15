package com.bsn.api.feedback;

import jakarta.validation.constraints.*;

public record FeedbackRequest(

        @Min(value = 0, message = "Rating should be between 0.0 and 5.0")
        @Max(value = 5, message = "Rating should be between 0.0 and 5.0")
        Double rating,

        @NotBlank(message = "Comment is mandatory")
        @Size(min = 8, max = 1000, message = "Comment must be between 8 and 1000 characters long")
        String comment,

        @NotNull(message = "Book ID must be provided")
        Long bookId
) {
}
