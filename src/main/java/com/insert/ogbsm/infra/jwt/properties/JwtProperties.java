package com.insert.ogbsm.infra.jwt.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;

@Getter
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private final String header;
    private final SecretKey secret;
    private final Long accessExp;
    private final Long refreshExp;
    private final String prefix;

    public JwtProperties(String header, String secret, Long accessExp, Long refreshExp, String prefix) {

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        this.header = header;
        this.secret = secretKey;
        this.accessExp = accessExp;
        this.refreshExp = refreshExp;
        this.prefix = prefix;
    }
}
