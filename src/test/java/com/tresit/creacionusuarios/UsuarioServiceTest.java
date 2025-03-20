package com.tresit.creacionusuarios;

import com.tresit.creacionusuarios.domain.Usuario;
import com.tresit.creacionusuarios.domain.UsuarioRequestDTO;
import com.tresit.creacionusuarios.domain.UsuarioResponseDTO;
import com.tresit.creacionusuarios.repository.UsuarioRepository;
import com.tresit.creacionusuarios.security.service.JwtService;
import com.tresit.creacionusuarios.service.UsuarioService;
import com.tresit.creacionusuarios.service.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ValidatorService validatorService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_exitoso() {

        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO("Juan Pérez", "juan@example.com", "Password123", Collections.emptyList());

        when(usuarioRepository.findByCorreo(requestDTO.getCorreo())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(requestDTO.getClave())).thenReturn("hashedPassword");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(UUID.randomUUID());
        usuarioMock.setClave("Clavedeprueba.1234");
        usuarioMock.setCorreo(requestDTO.getCorreo());
        usuarioMock.setTelefonos(Collections.emptyList());
        usuarioMock.setFechaCreacion(LocalDateTime.now());
        usuarioMock.setNombre(requestDTO.getNombre());

        usuarioService.setJwtService(jwtService);
        usuarioService.setAuthenticationManager(authenticationManager);

        when(jwtService.generarToken(any(Usuario.class))).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqdWFuQGV4YW1wbGUuY29tIiwiaWF0IjoxNjMwMzg3NjY1LCJleHBpcnkiOjE2MzAzODc2MjUsInJvbGUiOiJ1c3VhcmlvYXV0b3IiLCJpc3MiOiJodHRwczovL3d3dy5leGFtcGxlLmNvbS9hdXRoIn0.O7kp9zPql_xVbA6Af1JfFhCpWQHhG68cU5EGZayYY38");

        UsuarioResponseDTO response = usuarioService.registrarUsuario(requestDTO);

        assertNotNull(response);
        assertEquals(requestDTO.getNombre(), response.getNombre());
        assertEquals(requestDTO.getCorreo(), response.getCorreo());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getToken());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

}
