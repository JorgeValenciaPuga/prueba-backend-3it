package com.tresit.creacionusuarios.service;

import com.tresit.creacionusuarios.domain.Usuario;
import com.tresit.creacionusuarios.exception.UsuarioException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidatorService {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+$";
    private static final String CLAVE_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d{2,}).{6,}$";

    public void validarCorreo(String correo) {
        if (!correo.matches(EMAIL_REGEX)) {
            throw new UsuarioException("El correo no tiene un formato válido.");
        }
    }

    public void validarClave(String clave) {
        if (!clave.matches(CLAVE_REGEX)) {
            throw new UsuarioException("La clave debe tener al menos una mayúscula, una minúscula y dos números.");
        }
    }

    public void validarCorreoExistente(Optional<Usuario> usuarioExistente){
        if (usuarioExistente.isPresent()) {
            throw new UsuarioException("El correo ya está registrado");
        }
    }
}
