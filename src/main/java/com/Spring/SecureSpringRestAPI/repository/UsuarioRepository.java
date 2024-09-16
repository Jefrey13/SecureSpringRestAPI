package com.Spring.SecureSpringRestAPI.repository;

import com.Spring.SecureSpringRestAPI.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
