package com.bsn.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {

    private Double rating;

    private String comment;

    private Long bookId;

    public FeedbackResponse(Feedback feedback) {
        this.rating = feedback.getRating();
        this.comment = feedback.getComment();
        this.bookId = feedback.getBook().getId();
    }
}
