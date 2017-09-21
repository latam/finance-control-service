package pl.mlata.financecontrolservice.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SaldoSummary {
	private BigDecimal saldo;
	private BigDecimal totalIncome;
	private BigDecimal totalExpense;
	
	public SaldoSummary() {
		saldo = new BigDecimal(0);
		totalIncome = new BigDecimal(0);
		totalExpense = new BigDecimal(0);
	}
}
