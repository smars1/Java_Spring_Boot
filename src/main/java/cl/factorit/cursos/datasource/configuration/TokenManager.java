package cl.factorit.cursos.datasource.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class TokenManager {
    public static final long TOKEN_VALIDITY = 3600;

    private final SecretKeySpec secretKeySpec;
    private final JwtParser jwtParser;
    
    public TokenManager(@Value("${jwt.secret}") String jwtSecret) {
        SignatureAlgorithm sa = SignatureAlgorithm.HS256;
        this.secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), sa.getJcaName());
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKeySpec).build();
    }

    public String generateJwtToken(String username) {
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(secretKeySpec).compact();
    }

    public boolean validateJwtToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return !isTokenExpired;
    }

    public String getSubject(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}