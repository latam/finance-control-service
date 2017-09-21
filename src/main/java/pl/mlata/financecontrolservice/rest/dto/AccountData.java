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
public class AccountData {
	private Long id;
	private String type;
	private String name;
	private String description;
	private Long parentId;
	private List<Long> childAccountsId;
	private BigDecimal funds;
}
