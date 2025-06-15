package com.bsn.api.user;

import com.bsn.api.auth.RegisterResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<RegisterResponse>> getAllOwnersOfBookByBookTitle(@RequestParam String bookTitle) {
        List<RegisterResponse> response = userService.findAllOwnersOfBookByTitle(bookTitle);
        return ResponseEntity.ok(response);
    }
}
