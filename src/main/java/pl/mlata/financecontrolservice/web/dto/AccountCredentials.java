package pl.mlata.financecontrolservice.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AccountCredentials {
	private String email;
    private String password;
}
