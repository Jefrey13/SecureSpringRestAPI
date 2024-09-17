package com.Spring.SecureSpringRestAPI.controller;

import com.Spring.SecureSpringRestAPI.security.config.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) {
        String username = jwtTokenUtil.extractUsername(refreshToken);
        if (jwtTokenUtil.validateToken(refreshToken, username)) {
            String newAccessToken = jwtTokenUtil.generateAccessToken(username);
            return ResponseEntity.ok(newAccessToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is invalid or expired");
        }
    }
}
