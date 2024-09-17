package com.Spring.SecureSpringRestAPI.service;

import com.Spring.SecureSpringRestAPI.model.Usuario;
import com.Spring.SecureSpringRestAPI.repository.UsuarioRepository;
import com.Spring.SecureSpringRestAPI.security.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceImpl implements IAuthService{
    private final JwtTokenUtil jwtTokenUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(JwtTokenUtil jwtTokenUtil, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        if (passwordEncoder.matches(password, usuario.getPassword())) {
            return jwtTokenUtil.generateAccessToken(usuario);
        } else {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        String username = jwtTokenUtil.extractUsername(refreshToken);
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return jwtTokenUtil.generateAccessToken(usuario);
    }

    @Override
    public Usuario register(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }
}
