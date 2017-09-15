package pl.mlata.financecontrolservice.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {
	private static final long serialVersionUID = 8789079981288120939L;
	private String token;

    public JwtExpiredTokenException(String message) {
        super(message);
    }

    public JwtExpiredTokenException(String message, String token) {
        super(message);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
