package com.example.userManagement.security;

import com.example.userManagement.model.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey123"; // screteKey we can give what we want

    private Key getSigningKey() {          // used to convert secret key into key object
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    
    public String generateToken(String email, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();//used to information about user inside that token-payload->claims
        claims.put("roles", roles.stream().map(Enum::name).collect(Collectors.toList()));
        //stream is used to convert set os roles into stream so that it can pass one by one into list
        //enum is used to get name of role
        // collector is used to collect all the item and converted into list

        return Jwts.builder() // to generate token using all three functionality - header , paylod(claims) , signature
                .setClaims(claims) // it has user details along with there role
                .setSubject(email) // used to identify user usind email which is very unique
                .setIssuedAt(new Date(System.currentTimeMillis())) // token issues time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // expire time should be in ms 
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) 
                .compact(); // will generate token and return back 
    }

    // get email
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // get roles
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return (List<String>) getClaims(token).get("roles");
    }

    // check whether token is right or wrong 
    public boolean validateToken(String token) {
        try {
            getClaims(token);  // validate token generated and it expire date 
            return true;
        } catch (JwtException e) { // invalide token
            return false;
        }
    }

    // used to get all user details which was stored in token < which means payload or claims >  
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
