package pl.mlata.financecontrolservice.configuration.security.authentication.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtSettings;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    @Autowired
    private JwtSettings jwtSettings;

    @Override
    public String extract(String payload) {
        if(StringUtils.isEmpty(payload)) {
            throw new AuthenticationServiceException("Authorization header cannot be empty.");
        }

        if(payload.length() < jwtSettings.getPrefix().length()) {
            throw new AuthenticationServiceException("Invalid authorization header length.");
        }

        return payload.substring(jwtSettings.getPrefix().length(), payload.length());
    }
}
