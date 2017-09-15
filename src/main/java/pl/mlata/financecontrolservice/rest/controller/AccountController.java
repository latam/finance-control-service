package pl.mlata.financecontrolservice.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import pl.mlata.financecontrolservice.rest.dto.RegistrationData;
import pl.mlata.financecontrolservice.rest.dto.UserData;
import pl.mlata.financecontrolservice.rest.service.UserService;

@RestController
public class AccountController {
	private UserService userService;

	public AccountController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/auth/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserData> registerNewAccount(@RequestBody @Valid RegistrationData registrationData) {
		UserData userData = userService.registerNewAccount(registrationData);
		return new ResponseEntity<>(userData, HttpStatus.CREATED);
    }
}
