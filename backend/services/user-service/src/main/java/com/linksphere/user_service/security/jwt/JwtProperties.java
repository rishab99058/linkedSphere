package com.linksphere.user_service.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtProperties {

    private String secret;
    private String issuer;

}
