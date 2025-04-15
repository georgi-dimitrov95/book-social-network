package com.bsn.api.feedback;

import com.bsn.api.misc.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<?> saveFeedback( @RequestBody @Valid FeedbackRequest feedbackRequest, Authentication authentication) {
        try {
            FeedbackResponse savedFeedback = feedbackService.saveFeedback(feedbackRequest, authentication);
            return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
        } catch (UsernameNotFoundException e) {
            String errorMessage = "Authenticated user not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> findAllFeedbacksByBook(
            @PathVariable("bookId") Long bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        PageResponse<FeedbackResponse> feedbackResponses = feedbackService.findAllFeedbacksByBook(bookId, page, size);
        return new ResponseEntity<>(feedbackResponses, HttpStatus.OK);
    }
}
