package pl.mlata.financecontrolservice.configuration.security.authentication;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "security.authentication")
@Getter @Setter
public class JwtSettings {
    private String header;
    private Integer expirationTime;
    private String issuer;
    private String signingKey;
    private String prefix;
}
