package com.acme.ordermanagement.common.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        UserDetails user = User.builder()
                .username(request.username())
                .password("N/A")
                .authorities(Collections.emptyList())
                .build();

        String token = jwtService.generateToken(user);

        log.info("JWT token generated for user={}", request.username());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    public record AuthRequest(String username, String password) {
    }

    public record AuthResponse(String token) {
    }
}
