package uz.community.javacommunity.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.constants.JwtConstants;
import uz.community.javacommunity.controller.domain.User;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class JwtService {
    public static final Duration JWT_TOKEN_VALIDITY = Duration.of(1, ChronoUnit.HOURS);

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(now.toEpochMilli()))
                .setExpiration(new Date(now.plus(JWT_TOKEN_VALIDITY).toEpochMilli()))
                .addClaims(Map.of(
                        JwtConstants.ROLES_CLAIM, user.getRoles())
                )
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> decode(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build();
        return jwtParser.parseClaimsJws(token);
    }
}
