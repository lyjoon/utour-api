package com.utour.service;

import com.utour.common.CommonService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginService extends CommonService {

    @Value("${_TOKEN_SECRET_KEY:}")
    private String secretKey;

    public String doLogin(String loginId, String password) {
        // TODO : member 정보 exists
        return this.makeToken(loginId);
    }

    private String makeToken(String loginId) {
        Date current = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(current)
                .setExpiration(new Date(current.getTime() + Duration.ofMinutes(30).toMillis()))
                .claim("id", loginId)
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    public Boolean expired(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Claims claims = Jwts.parser()
                .setSigningKey(this.secretKey) // (3)
                .parseClaimsJws(token) // (4)
                .getBody();

        return claims.isEmpty();
    }
}
