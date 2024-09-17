package com.Spring.SecureSpringRestAPI.controller;

import com.Spring.SecureSpringRestAPI.model.Usuario;
import com.Spring.SecureSpringRestAPI.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    @Autowired
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        String token = authService.login(username, password);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('PERMISO_CREAR_USUARIO')")  // Solo usuarios con este permiso pueden registrar
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = authService.register(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestParam String refreshToken) {
        String newAccessToken = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(newAccessToken);
    }
}
