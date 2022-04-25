package com.utour.service;

import com.utour.common.CommonService;
import com.utour.entity.User;
import com.utour.exception.AuthenticationException;
import com.utour.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoginService extends CommonService {

    private final UserMapper userMapper;

    @Value("${app.login.secret-key:utour}")
    private String secretKey;
    private Key key;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void postConstruct() {
        this.key = new SecretKeySpec(this.secretKey.getBytes(Charset.defaultCharset()), this.signatureAlgorithm.getJcaName());
    }

    public String doLogin(String loginId, String password) {
        User user = this.userMapper.findById(User.builder().userId(loginId).build());

        if(Objects.isNull(user) || !user.getPassword().equals(password)) {
            throw new AuthenticationException(getMessage("error.service.login.invalid"));
        }

        return this.makeToken(user);
    }

    private String makeToken(User user) {
        Date current = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(current)
                .setExpiration(new Date(current.getTime() + Duration.ofMinutes(60).toMillis()))
                .claim("userId", user.getUserId())
                .signWith(this.signatureAlgorithm, this.key)
                .compact();
    }

    public Boolean isExpired(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Claims claims = Jwts.parser()
                .setSigningKey(this.secretKey) // (3)
                .parseClaimsJws(token) // (4)
                .getBody();

        return claims.isEmpty();
    }
}
