package com.tresit.creacionusuarios.service;

import com.tresit.creacionusuarios.domain.*;
import com.tresit.creacionusuarios.exception.UsuarioException;
import com.tresit.creacionusuarios.repository.UsuarioRepository;
import com.tresit.creacionusuarios.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ValidatorService validatorService;

    @Autowired
    JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usuarioRepository, ValidatorService validatorService) {
        this.usuarioRepository = usuarioRepository;
        this.validatorService = validatorService;
    }

    @Transactional
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO request) {

        try{
            Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(request.getCorreo());

            validatorService.validarCorreoExistente(usuarioExistente);

            validatorService.validarCorreo(request.getCorreo());

            validatorService.validarClave(request.getClave());

            String claveEncriptada = passwordEncoder.encode(request.getClave());

            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setCorreo(request.getCorreo());
            usuario.setClave(claveEncriptada);
            usuario.setFechaCreacion(LocalDateTime.now());

            List<Telefono> telefonos = request.getTelefonos().stream()
                    .map(t -> new Telefono(t.getNumero(), t.getCodigoArea(), t.getCodigoPais(), usuario))
                    .toList();
            usuario.setTelefonos(telefonos);

            usuarioRepository.save(usuario);

            String token = jwtService.generarToken(usuario);

            return new UsuarioResponseDTO(usuario.getId(),usuario.getNombre(),usuario.getCorreo(),usuario.getFechaCreacion(),usuario.getTelefonos().stream()
                    .map(t -> new TelefonoResponseDTO(t.getNumero(), t.getCodigoArea(), t.getCodigoPais()))
                    .collect(Collectors.toList()),token );
        }catch(Exception e){
            throw e;
        }
        
    }

    public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {

        try{
            List<Usuario> usuarios = usuarioRepository.findAll();

            return usuarios.stream().map(usuario -> new UsuarioResponseDTO(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getFechaCreacion(),
                    usuario.getTelefonos().stream()
                            .map(t -> new TelefonoResponseDTO(t.getNumero(), t.getCodigoArea(), t.getCodigoPais()))
                            .collect(Collectors.toList()),
                    ""
            )).collect(Collectors.toList());
        }catch(UsuarioException e){
            throw e;
        }

    }

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


}