package com.Spring.SecureSpringRestAPI.service;

import com.Spring.SecureSpringRestAPI.model.Usuario;

public interface IAuthService {
    String login(String username, String password);
    String refreshAccessToken(String refreshToken);
    Usuario register(Usuario usuario);
}