package com.bsn.api.auth;

import com.bsn.api.role.Role;
import com.bsn.api.user.User;
import com.bsn.api.role.RoleRepository;
import com.bsn.api.user.UserPrincipal;
import com.bsn.api.user.UserRepository;
import com.bsn.api.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder;

    public User registerUser(RegisterRequest registerRequest) throws IllegalArgumentException, InternalException {
        try {
            registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
            User user = new User(registerRequest);
            Role role = roleRepository.findByName("USER").orElseThrow(() -> new EntityNotFoundException("USER role not found"));
            user.getRoles().add(role);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("The email is already in use");
        } catch (EntityNotFoundException e) {
            throw new InternalException("Something went wrong. Please try again later");
        }
    }

    public String login(LoginRequest loginRequest) throws BadCredentialsException {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            return jwtService.generateToken(loginRequest.getEmail());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return userRepository.findByEmail(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
