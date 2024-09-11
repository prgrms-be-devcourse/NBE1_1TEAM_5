package com.example.gccoffee.configuration;

import com.example.gccoffee.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${app.secret-key}")
    private String SECRET_KEY;


    public String createAccessToken(User user, long expireTime) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setSubject(user.getEmail().getAddress())
                .setIssuer("dev-team-5")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

/*    public String createRefreshToken(User user, long expireTime) {


    }*/
}
