package com.example.gccoffee.auth.withRefresh;

import com.example.gccoffee.repository.user.UserRefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${app.secret-key}")
    private String SECRET_KEY;
    @Value("${app.access-expire-time}")
    private long accessExpireTime;
    @Value("${app.refresh-expire-time}")
    private long refreshExpireTime;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRefreshTokenRepository tokenRepository;

    public String createAccessToken(String specification) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuer("dev-team-5")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpireTime))
                .setSubject(specification)
                .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setIssuer("dev-team-5")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpireTime))
                .compact();
    }

    public String recreateAccessToken(String accessToken) throws JsonProcessingException {
        String subject = decodeJwtPayload(accessToken);


        return createAccessToken(subject);
    }

    public void validateRefreshToken(String refreshToken, String accessToken) throws JsonProcessingException {
        validateAndParseToken(refreshToken);
        String email = decodeJwtPayload(accessToken).split(":")[0];

    }

    private Jws<Claims> validateAndParseToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token);
    }

    // toString() 주의
    private String decodeJwtPayload(String accessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(accessToken.split("\\.")[1])
                        , StandardCharsets.UTF_8), Map.class)
                .get("email").toString();
    }
}
