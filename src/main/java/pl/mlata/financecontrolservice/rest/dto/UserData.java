package pl.mlata.financecontrolservice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
	private String firstName;
	private String lastName;
	private String email;
}
