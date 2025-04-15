package com.bsn.api.security;

import com.bsn.api.user.UserPrincipalService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserPrincipalService userPrincipalService;

    public JwtFilter(JwtService jwtService, UserPrincipalService userPrincipalService) {
        this.jwtService = jwtService;
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        extract the Authorization header from the request
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String email = null;

//        check if the Authorization header contains a Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtService.extractEmail(token);
        }

//        check if an email was extracted and the user is NOT yet authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userPrincipal = userPrincipalService.loadUserByUsername(email);

//            validate the token and set the authentication object
            if (jwtService.isTokenValid(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
