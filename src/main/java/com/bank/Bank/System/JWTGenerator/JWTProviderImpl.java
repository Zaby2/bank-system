package com.bank.Bank.System.JWTGenerator;

import com.bank.Bank.System.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTProviderImpl implements JWTProvider {

    private String SECRET_KEY = generateSecretKey();
    private static final long EXPIRATION_TIME = 86400000;
    @Override
    public String generateAccessToken(@NonNull UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDTO.getLogin());
        claims.put("password", userDTO.getPassword());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDTO.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public String generateSecretKey() {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacSHA512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


        keyGen.init(512);


        SecretKey secretKey = keyGen.generateKey();
        byte[] keyBytes = secretKey.getEncoded();


        String base64Key = java.util.Base64.getEncoder().encodeToString(keyBytes);

        return base64Key;
    }


}
