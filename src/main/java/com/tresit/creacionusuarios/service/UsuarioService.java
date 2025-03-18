package com.tresit.creacionusuarios.service;

import com.tresit.creacionusuarios.domain.Telefono;
import com.tresit.creacionusuarios.domain.Usuario;
import com.tresit.creacionusuarios.domain.UsuarioRequestDTO;
import com.tresit.creacionusuarios.domain.UsuarioResponseDTO;
import com.tresit.creacionusuarios.exception.UsuarioException;
import com.tresit.creacionusuarios.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final String secretKey = "claveSecretaSegura"; // Debería estar en variables de entorno

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {

        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(request.getCorreo());
        if (usuarioExistente.isPresent()) {
            throw new UsuarioException("El correo ya está registrado");
        }

        if (!esCorreoValido(request.getCorreo())) {
            throw new UsuarioException("El formato del correo es incorrecto. Ejemplo válido: usuario@dominio.com");
        }

        if (!esClaveValida(request.getClave())) {
            throw new UsuarioException("La clave debe tener al menos una letra mayúscula, una minúscula, dos números y un mínimo de 6 caracteres.");
        }

        String claveEncriptada = passwordEncoder.encode(request.getClave());

        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNombre(request.getNombre());
        usuario.setCorreo(request.getCorreo());
        usuario.setClave(claveEncriptada);
        usuario.setFechaCreacion(LocalDateTime.now());

        List<Telefono> telefonos = request.getTelefonos().stream()
                .map(t -> new Telefono(t.getNumero(), t.getCodigoArea(), t.getCodigoPais(), usuario))
                .toList();
        usuario.setTelefonos(telefonos);

        usuarioRepository.save(usuario);

        String token = generarToken(usuario);

        return new UsuarioResponseDTO(usuario.getId(), usuario.getFechaCreacion(), token);
    }


    private boolean esCorreoValido(String correo) {
        String regexCorreo = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regexCorreo, correo);
    }


    private boolean esClaveValida(String clave) {
        String regexClave = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d.*\\d).{6,}$";
        return Pattern.matches(regexClave, clave);
    }


    private String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000)) // 1 día de validez
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}