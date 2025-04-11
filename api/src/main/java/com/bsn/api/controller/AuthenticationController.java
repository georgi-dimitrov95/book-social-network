package com.bsn.api.controller;

import com.bsn.api.model.LoginUserDTO;
import com.bsn.api.model.RegisterResponseDTO;
import com.bsn.api.model.RegisterUserDTO;
import com.bsn.api.model.User;
import com.bsn.api.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterUserDTO registerRequest) {
        try {
            User user = authService.registerUser(registerRequest);
            RegisterResponseDTO response = new RegisterResponseDTO(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        } catch (InternalException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    need to return a more suitable object and implement better exception handling
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        try {
            String token = authService.login(loginUserDTO);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
}
