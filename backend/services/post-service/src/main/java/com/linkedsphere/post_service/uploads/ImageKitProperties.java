package com.linkedsphere.post_service.uploads;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "imagekit")
public class ImageKitProperties {

    private String publicKey;
    private String privateKey;
    private String urlEndpoint;
}
