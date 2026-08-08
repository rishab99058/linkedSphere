package com.linksphere.auth_service.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt") // can be used instead of multiple $values
public class JwtProperties {

    private String secret;

    private Long accessTokenExpiration;

    private Long refreshTokenExpiration;

    private String issuer;

}
