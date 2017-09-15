package pl.mlata.financecontrolservice.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class RegistrationData {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
}
