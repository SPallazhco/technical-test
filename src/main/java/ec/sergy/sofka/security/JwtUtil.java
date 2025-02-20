package ec.sergy.sofka.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "cf63cc58-959d-4219-9d6a-c488a6e5f005";

    // Convertimos la clave en una SecretKey válida
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Genera un token con el username
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(key, Jwts.SIG.HS256) // Usamos el nuevo método
                .compact();
    }

    // Obtiene el username desde el token
    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();
        return parser.parseSignedClaims(token).getPayload().getSubject();
    }

    // Valida si el token es correcto
    public boolean validateToken(String token, String username) {
        try {
            return getUsernameFromToken(token).equals(username) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false; // Token inválido o mal formado
        }
    }

    // Verifica si el token ha expirado
    private boolean isTokenExpired(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();
        Date expiration = parser.parseSignedClaims(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }
}
