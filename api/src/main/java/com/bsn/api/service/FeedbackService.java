package com.bsn.api.service;

import com.bsn.api.model.*;
import com.bsn.api.repository.BookRepository;
import com.bsn.api.repository.FeedbackRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final AuthenticationService authenticationService;

    private final FeedbackRepository feedbackRepository;

    private final BookRepository bookRepository;

    public FeedbackResponse saveFeedback(FeedbackRequest feedbackRequest, Authentication authentication) {
        User user = authenticationService.getAuthenticatedUser(authentication);
        Book book = bookRepository.findById(feedbackRequest.bookId()).orElseThrow(EntityExistsException::new);

        if (book.isArchived() || !book.isShareable()) {
            throw new AccessDeniedException("You can't give a feedback to an archived or non-shareable book");
        }

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new AccessDeniedException("You can't give a feedback to your own book");
        }

        Feedback feedback = new Feedback();
        feedback.setRating(feedbackRequest.rating());
        feedback.setComment(feedbackRequest.comment());
        feedback.setBook(book);

        return new FeedbackResponse(feedbackRepository.save(feedback));
    }
}
