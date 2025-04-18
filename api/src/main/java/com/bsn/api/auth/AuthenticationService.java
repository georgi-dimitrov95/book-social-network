package com.bsn.api.auth;

import com.bsn.api.role.Role;
import com.bsn.api.user.User;
import com.bsn.api.role.RoleRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private AuthenticationManager authManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder;

    public User registerUser(RegisterUserDTO registerUserDTO) throws IllegalArgumentException, InternalException {
        try {
            registerUserDTO.setPassword(encoder.encode(registerUserDTO.getPassword()));
            User user = new User(registerUserDTO);
            Role role = roleRepository.findByName("USER").orElseThrow(() -> new EntityNotFoundException("USER role not found"));
            user.getRoles().add(role);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException();
        } catch (EntityNotFoundException e) {
            throw new InternalException("User role not present in DB");
        }
    }

    public String login(LoginUserDTO loginUserDTO) throws BadCredentialsException {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword()));
            return jwtService.generateToken(loginUserDTO.getEmail());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public User getAuthenticatedUser(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
