package com.tresit.creacionusuarios.repository;

import com.tresit.creacionusuarios.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByCorreo(String correo);

}
