package pl.mlata.financecontrolservice.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	private Long id;
	private String type;
	private String name;
	private String description;
	private List<AccountDto> childAccounts;
	private BigDecimal funds;
}
