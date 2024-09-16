package com.Spring.SecureSpringRestAPI.repository;

import com.Spring.SecureSpringRestAPI.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
