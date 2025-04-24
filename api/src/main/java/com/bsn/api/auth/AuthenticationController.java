package com.bsn.api.auth;

import com.bsn.api.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        User user = authService.registerUser(registerRequest);
        RegisterResponse response = new RegisterResponse(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
