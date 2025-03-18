package com.tresit.creacionusuarios.controller;

import com.tresit.creacionusuarios.domain.UsuarioRequestDTO;
import com.tresit.creacionusuarios.domain.UsuarioResponseDTO;
import com.tresit.creacionusuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        try {
            UsuarioResponseDTO usuarioCreado = usuarioService.registrarUsuario(request);
            return ResponseEntity.status(201).body(usuarioCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

