package com.Chatify.Security;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtill {
   // ✅ Use strong, random strings as secret keys
private final String AccessSecretKey = "my-access-secret-key-12345678901234567890123456789012";
private final String RefreshSecretKey = "my-refresh-secret-key-98765432109876543210987654321098";

    private final int jwtExpirationMs=60000;

    // method to convert into key
    private Key getAccessKey()
    {
        return Keys.hmacShaKeyFor(AccessSecretKey.getBytes());
    }

    private Key getRefreshKey()
    {
        return Keys.hmacShaKeyFor(RefreshSecretKey.getBytes());
    }

    public String generateAccessToken(String username)
    {
        return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs))
                    .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                    .compact();
    }

 

    public boolean validateAccessToken(String token)
    {
        try {
              Jwts.parserBuilder().setSigningKey(getAccessKey()).build().parseClaimsJws(token);
              return true;
        } catch (JwtException | IllegalArgumentException e) {
          return false;
        }
    }

       public String generateRefreshToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+7*24*60*60*1000))
                .signWith(getRefreshKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateRefereshToken(String token)
    {
        try {
            Jwts.parserBuilder().setSigningKey(getRefreshKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    } 

    public String getUsernameFromToken(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getAccessKey())
        .build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getUsernameFromRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getRefreshKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
