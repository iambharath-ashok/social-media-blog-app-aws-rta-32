package com.bharath.leanring.blog.socialmediablogapp.security;

import com.bharath.leanring.blog.socialmediablogapp.exception.BlogAPIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtility {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


    //Generate new token for Users
    public String generateJwtToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(currentDate)
                        .setExpiration(expireDate)
                        .signWith(key())
                        .compact();
        return token;
    }


    //Validate the token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(key()).build().parse(token);
            return true;
        } catch (Exception e ) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
        }
    }



    //Create a secrete Key
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsername(String token) {
      Claims claims =   Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
      String username = claims.getSubject();
      return username;
    }
}
