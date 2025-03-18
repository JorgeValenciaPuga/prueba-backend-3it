package com.tresit.creacionusuarios.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "telefonos")
public class Telefono {

    public Telefono() {}

    public Telefono(String numero, String codigoArea, String codigoPais, Usuario usuario) {
        this.numero = numero;
        this.codigoArea = codigoArea;
        this.codigoPais = codigoPais;
        this.usuario = usuario;
    }

    @Id
    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String codigoArea;

    @Column(nullable = false)
    private String codigoPais;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefono telefono = (Telefono) o;
        return Objects.equals(numero, telefono.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
