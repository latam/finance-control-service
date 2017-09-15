package pl.mlata.financecontrolservice.configuration.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import pl.mlata.financecontrolservice.persistance.model.User;

import java.util.Collection;

public class JwtTokenAuthentication extends AbstractAuthenticationToken {
	private static final long serialVersionUID = -906826759093033093L;
	private String token;
    private User user;

    public JwtTokenAuthentication(Collection<? extends GrantedAuthority> authorities, User user) {
        super(authorities);
        this.eraseCredentials();
        this.user = user;
        super.setAuthenticated(true);
    }

    public JwtTokenAuthentication(String token) {
        super(null);
        this.token = token;
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.token = null;
    }
}
