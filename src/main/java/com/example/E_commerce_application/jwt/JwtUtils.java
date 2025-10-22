package com.example.E_commerce_application.jwt;


import com.example.E_commerce_application.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    private final String secretKey = "dontgettoodeepilyinfatuatedsomeonewithoutreasonitwillhurtyousomuchwhenloveisgoneyouwillnevereasilyescape";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    public String generateToken(Long id, String email, Role role){
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("email", email)
                .claim("role", role.name())
                .issuer("/api/v1/users/login")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey())
                .compact();
    }

    public String extractEmail(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        Date today = Date.from(Instant.now());
        return getClaims(token).getExpiration().before(today);
    }

    public boolean validateToken(String token, String email){
        return !isTokenExpired(token) && extractEmail(token).equals(email);
    }

    public boolean isTokenValid(String token){
        try{
            getClaims(token);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Claims getClaims(String token){
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSecretKey(){
        byte[] decodedPasswords = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedPasswords);
    }

}
