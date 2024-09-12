package com.example.gccoffee.auth;

import com.example.gccoffee.model.user.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class MyJwtTokenProvider {

    @Value("${app.secret-key}")
    private String SECRET_KEY;
    @Value("${app.access-expire-time}")
    private long accessExpireTime;

    public String createMyToken(User entity, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(entity.getEmail().getAddress());
        claims.put("roles", roles);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, getTokenKey())
                .setIssuer("dev-team-5")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpireTime))
                .setClaims(claims)
                .compact();
    }

    public List<String> getRoles(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return (List<String>) claims.get("roles");
    }


    public String validateUserToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJwt(token)
                    .getBody();
            return claims.getSubject();
        } catch (SecurityException ex){
            throw new JwtException("잘못된 jwt 시그니처 입니다!!");
        } catch (MalformedJwtException ex){
            throw new JwtException(("유효하지 않은 토큰입니다! MalformedJwtException"));
        } catch(ExpiredJwtException ex){
            throw new JwtException("유효기간이 만료된 토큰입니다!");
        } catch(RuntimeException ex){
            ex.printStackTrace();
            throw ex;
        }
    }

    // 토큰 발급과 토큰 유효성 검사에 사용되는 키 생성
    private SecretKey getTokenKey(){
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
