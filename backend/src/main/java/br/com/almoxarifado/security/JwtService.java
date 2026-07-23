package br.com.almoxarifado.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "MinhaChaveSuperSecretaJWTParaAlmoxarifado2026";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes());

    public String gerarToken(String username,String role,Long prefeituraId) {

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("prefeituraId", prefeituraId)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )
                .signWith(key)
                .compact();
    }

    public Claims extrairClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extrairUsername(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {

        return extrairClaims(token)
                .getExpiration()
                .after(new Date());
    }
    
    public Long extrairPrefeituraId(
        String token) {

    return extrairClaims(token)
            .get("prefeituraId", Long.class);
}
}
