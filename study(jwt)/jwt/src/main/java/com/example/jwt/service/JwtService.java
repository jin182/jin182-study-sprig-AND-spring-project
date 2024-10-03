package com.example.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
@Slf4j
@Service
public class JwtService {

    private static final String secretKey = "java11SpringBootJWTTokenIssueMethod";
    public String create(Map<String, Object> claims,
                         LocalDateTime expireAt
                         ) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .claims(claims)
                .expiration(_expireAt)
                .signWith(key)
                .compact();

    }
    public void Validation(String token) {
        var key = Keys.hmacShaKeyFor(secretKey.getBytes());

        var parser =   Jwts.parser()
                .verifyWith(key)
                .build();
        try {
            var result = parser.parseSignedClaims(token);

            result.getPayload().entrySet().forEach(value -> {
                log.info("Key : {}, value: {}", value.getKey(), value.getValue());
            });
        } catch (Exception e) {
            if(e instanceof SignatureException) {
                throw new RuntimeException("JWT Token Not Valid Exception");
            } else if (e instanceof ExpiredJwtException) {
                throw new RuntimeException("JWT Token Expired Exception");
            } else  {
                throw  new  RuntimeException("JWT Token Validation Exception");
            }
        }




    }

}
