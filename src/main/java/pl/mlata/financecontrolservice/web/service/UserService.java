package pl.mlata.financecontrolservice.web.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.mlata.financecontrolservice.configuration.security.authentication.JwtTokenAuthentication;
import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.persistance.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("User '" + email + "' not found."));
    }
	
	@Transactional
    public void registerNewAccount(User userData) {
        String encodedPassword = passwordEncoder.encode(userData.getPassword());
        userData.setPassword(encodedPassword);

        userData = userRepository.save(userData);
    }

    public User getCurrentUser() {
        JwtTokenAuthentication userAuth = (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userAuth.getPrincipal();

        return user;
    }
}
