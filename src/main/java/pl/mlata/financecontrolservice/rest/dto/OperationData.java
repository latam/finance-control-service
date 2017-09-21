package pl.mlata.financecontrolservice.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationData {
	private Long id;
	private String type;
	private String date;
	private Long toAccountId;
	private Long fromAccountId;
	private BigDecimal funds;
}
