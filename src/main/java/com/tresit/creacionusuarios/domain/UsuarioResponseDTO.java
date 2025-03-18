package com.tresit.creacionusuarios.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioResponseDTO {
    private UUID id;
    private LocalDateTime createdAt;
    private String token;

    public UsuarioResponseDTO(UUID id, LocalDateTime createdAt, String token) {
        this.id = id;
        this.createdAt = createdAt;
        this.token = token;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
