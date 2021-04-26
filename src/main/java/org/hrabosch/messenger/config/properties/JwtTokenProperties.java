package org.hrabosch.messenger.config.properties;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt")
@Data
@Configuration
public class JwtTokenProperties {

    private String authorizationHeader = "Authorization";
    private String secretKey = "changeMe";
    private long validity = 3600000;
}
