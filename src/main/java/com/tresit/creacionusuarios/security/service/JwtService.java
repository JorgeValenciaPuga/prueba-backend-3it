package com.tresit.creacionusuarios.security.service;

import com.tresit.creacionusuarios.domain.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "secretkey123456secretkeyrrhfjrhfjrhfhrfhruhuvfv";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000)) // 1 día de validez
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String obtenerCorreoDesdeToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        String correo = obtenerCorreoDesdeToken(token);
        return correo.equals(userDetails.getUsername()) && !estaExpirado(token);
    }

    private boolean estaExpirado(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}

