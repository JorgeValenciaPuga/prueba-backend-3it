package com.tresit.creacionusuarios.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UsuarioResponseDTO {
    private UUID id;
    private String nombre;
    private String correo;
    private LocalDateTime createdAt;
    private List<TelefonoResponseDTO> telefonos;
    private String token;

    public UsuarioResponseDTO(UUID id, String nombre, String correo, LocalDateTime createdAt, List<TelefonoResponseDTO> telefonos, String token) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.createdAt = createdAt;
        this.telefonos = telefonos;
        this.token = token;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TelefonoResponseDTO> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<TelefonoResponseDTO> telefonos) {
        this.telefonos = telefonos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

