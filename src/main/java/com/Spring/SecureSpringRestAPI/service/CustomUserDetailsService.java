package com.Spring.SecureSpringRestAPI.service;

import com.Spring.SecureSpringRestAPI.model.Rol;
import com.Spring.SecureSpringRestAPI.model.Usuario;
import com.Spring.SecureSpringRestAPI.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPassword(),
                getAuthorities(usuario.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Rol> roles) {
        return roles.stream()
                .flatMap(rol -> rol.getPermisos().stream())
                .map(permiso -> new org.springframework.security.core.authority.SimpleGrantedAuthority(permiso.getName()))
                .collect(Collectors.toList());
    }
}