package com.bsn.api.controller;

import com.bsn.api.model.FeedbackRequest;
import com.bsn.api.model.FeedbackResponse;
import com.bsn.api.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
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
}
