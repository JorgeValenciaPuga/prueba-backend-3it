package com.tresit.creacionusuarios.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo es inválido")
    private String correo;

    @NotBlank(message = "La clave es obligatoria")
    @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*\\d).+$",
            message = "La clave debe contener al menos una letra mayúscula, una minúscula y dos números")
    private String clave;

    private List<TelefonoRequestDTO> telefonos;

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<TelefonoRequestDTO> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<TelefonoRequestDTO> telefonos) {
        this.telefonos = telefonos;
    }
}

