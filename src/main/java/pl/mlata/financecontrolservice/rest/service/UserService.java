package pl.mlata.financecontrolservice.rest.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import global.UserRoles;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtTokenAuthentication;
import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.persistance.repository.UserRepository;
import pl.mlata.financecontrolservice.rest.dto.RegistrationData;

@Service
public class UserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		
		modelMapper = new ModelMapper();
	}

	public User getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UsernameNotFoundException("User '" + email + "' not found."));
    }
	
    public void registerNewAccount(RegistrationData registrationData) {
        String encodedPassword = passwordEncoder.encode(registrationData.getPassword());
        User userData = modelMapper.map(registrationData, User.class);
        userData.setPassword(encodedPassword);
        userData.setRoles(UserRoles.User.toString());
        
        userRepository.save(userData);
    }

    public User getCurrentUser() {
        JwtTokenAuthentication userAuth = (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userAuth.getPrincipal();
    
        return user;
    }
}
