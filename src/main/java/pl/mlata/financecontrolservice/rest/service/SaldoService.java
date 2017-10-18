package pl.mlata.financecontrolservice.rest.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import global.AccountTypes;
import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.persistance.repository.AccountRepository;
import pl.mlata.financecontrolservice.rest.dto.SaldoSummary;

@Service
public class SaldoService {
	private AccountRepository accountRepository;
	private SaldoHelper saldoHelper;

	public SaldoService(AccountRepository accountRepository, SaldoHelper saldoHelper) {
		this.accountRepository = accountRepository;
		this.saldoHelper = saldoHelper;
	}

	public SaldoSummary calculateSaldoSummary(User user) throws Exception {
		SaldoSummary summary = new SaldoSummary();
		List<Account> rootAccounts = accountRepository.findByParentAccountIsNullAndUser(user);
		for(Account account : rootAccounts) {
			BigDecimal value = saldoHelper.calculateFundsForAccount(account);
			
			if(account.getType().equals(AccountTypes.Expense.toString())) {
				summary.setTotalExpense(value);
			}
			else if(account.getType().equals(AccountTypes.Income.toString())) {
				summary.setTotalIncome(value);
			}
		}
		
		BigDecimal saldo = summary.getTotalIncome().subtract(summary.getTotalExpense());
		summary.setSaldo(saldo);
		
		return summary;
	}
}
