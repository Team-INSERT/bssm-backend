package com.insert.ogbsm.infra.oauth;

import leehj050211.bsmOauth.BsmOauth;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Getter
@ConfigurationProperties(prefix = "bsm")
public class BsmOauthProperties {

    private final String client_id;
    private final String secret_key;

    public BsmOauthProperties(String client_id, String secret_key) {
        this.client_id = client_id;
        this.secret_key = secret_key;
    }

    @Bean("bsmOauth")
    public BsmOauth bsmOauth() {
        return new BsmOauth(client_id, secret_key);
    }
}

