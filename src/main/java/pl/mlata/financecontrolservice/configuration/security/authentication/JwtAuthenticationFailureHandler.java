package pl.mlata.financecontrolservice.configuration.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.mlata.financecontrolservice.exception.JwtExpiredTokenException;
import pl.mlata.financecontrolservice.web.dto.ErrorCode;
import pl.mlata.financecontrolservice.web.dto.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;

    @Autowired
    public JwtAuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse;
        if(e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            errorResponse = new ErrorResponse("Invalid username or password", e.getMessage(), ErrorCode.BAD_CREDENTIALS);
        }
        else if(e instanceof JwtExpiredTokenException) {
            errorResponse = new ErrorResponse("Authorization token has expired.", e.getMessage(), ErrorCode.TOKEN_EXPIRED);
        }
        else {
            errorResponse = new ErrorResponse("Authentication error", e.getMessage(), ErrorCode.AUTHENTICATION);
        }

        mapper.writeValue(httpServletResponse.getWriter(), errorResponse);
    }
}
