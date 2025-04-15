package com.bsn.api.feedback;

import com.bsn.api.auth.AuthenticationService;
import com.bsn.api.book.Book;
import com.bsn.api.misc.*;
import com.bsn.api.book.BookRepository;
import com.bsn.api.user.User;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Long bookId, int page, int size) {
//        User user = authenticationService.getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacksPage = feedbackRepository.findByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = feedbacksPage.getContent()
                .stream()
                .map(FeedbackResponse::new)
                .toList();

        return new PageResponse<>(feedbackResponses, feedbacksPage);
    }
}
