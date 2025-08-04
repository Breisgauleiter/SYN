package org.syntopia.consciousness.platform.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;
import org.syntopia.consciousness.platform.dto.auth.TokenResponseDto;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Sacred JWT Token Service
 * Handles JWT token generation and validation
 */
@Service
public class JwtTokenService {

    @Value("${app.jwt.secret:SacredConsciousnessTokenSecretForSyntopia2024}")
    private String jwtSecret;

    @Value("${app.jwt.access-token-expiration:3600}") // 1 hour
    private long accessTokenExpiration;

    @Value("${app.jwt.refresh-token-expiration:604800}") // 7 days
    private long refreshTokenExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generate access and refresh tokens for user
     */
    public TokenResponseDto generateTokens(SyntopiaUser user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        return new TokenResponseDto(accessToken, refreshToken, accessTokenExpiration);
    }

    /**
     * Generate access token
     */
    private String generateAccessToken(SyntopiaUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration * 1000);

        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("consciousnessLevel", user.getConsciousnessLevel())
                .claim("type", "access")
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Generate refresh token
     */
    private String generateRefreshToken(SyntopiaUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpiration * 1000);

        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("type", "refresh")
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Get user ID from token
     */
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("🔴 Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get token expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    /**
     * Check if token is expired
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
