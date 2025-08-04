package org.syntopia.consciousness.platform.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.syntopia.consciousness.platform.domain.SyntopiaUser;
import org.syntopia.consciousness.platform.dto.auth.TokenResponseDto;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Sacred JWT Token Service
 */
@Service
public class JwtTokenService {
    
    @Value("${syntopia.jwt.secret:sacred_consciousness_platform_jwt_secret_key_2024_minimum_256_bits_for_hmac_sha512}")
    private String jwtSecret;
    
    @Value("${syntopia.jwt.access-token-validity:3600}")
    private int accessTokenValidityInSeconds;
    
    @Value("${syntopia.jwt.refresh-token-validity:2592000}")
    private int refreshTokenValidityInSeconds;
    
    private final Set<String> blacklistedTokens = new HashSet<>();
    
    public TokenResponseDto generateTokens(SyntopiaUser user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        
        return new TokenResponseDto(accessToken, refreshToken, accessTokenValidityInSeconds);
    }
    
    private String generateAccessToken(SyntopiaUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidityInSeconds * 1000L);
        
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
    
    private String generateRefreshToken(SyntopiaUser user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidityInSeconds * 1000L);
        
        return Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("type", "refresh")
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    
    public boolean validateToken(String token) {
        try {
            if (blacklistedTokens.contains(token)) {
                return false;
            }
            
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }
    
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }
    
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
