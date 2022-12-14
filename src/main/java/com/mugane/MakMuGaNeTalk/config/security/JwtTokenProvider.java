package com.mugane.MakMuGaNeTalk.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mugane.MakMuGaNeTalk.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret_key}")
    private String secretKey;

    private final UserDetailsService userDetailsService;
    private final long refreshTokenValidTime = 30 * 24 * 60 * 60 * 1000L; // 30일
    private final long accessTokenValidTime = 30 * 60 * 1000L; // 30분

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public TokenDto createTokenDto(Long userId, String userPk, String nickname,
        List<String> roles) {
        String accessToken = createAccessToken(userId, userPk, nickname, roles);
        String refreshToken = createRefreshToken(userId, userPk, nickname, roles);
        return TokenDto.builder()
            .grantType("bearer")
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .accessTokenExpireDate(accessTokenValidTime)
            .build();

    }

    public String createAccessToken(Long userId, String userPk, String nickname,
        List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("nickname", nickname);
        claims.put("roles", roles);
        claims.put("userId", userId);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String createRefreshToken(Long userId, String userPk, String nickname,
        List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("nickname", nickname);
        claims.put("roles", roles);
        claims.put("userId", userId);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        HashMap<String, String> payloadMap = getPayloadByToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(payloadMap.get("sub"));
        return new UsernamePasswordAuthenticationToken(userDetails, token,
            userDetails.getAuthorities());
    }


    public static HashMap<String, String> getPayloadByToken(String token) {
        try {
            String[] splitJwt = token.split("\\.");

            Base64.Decoder decoder = Base64.getDecoder();
            String payload = new String(decoder.decode(splitJwt[1].getBytes()));

            return new ObjectMapper().readValue(payload, HashMap.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return new HashMap<>();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {
            log.error("Invalid JWT signature", e);
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
            throw e;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
            throw e;
        }
    }
}
