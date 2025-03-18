package com.tresit.creacionusuarios.repository;

import com.tresit.creacionusuarios.domain.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefonoRepository extends JpaRepository<Telefono, Long> {
}
