package pl.mlata.financecontrolservice.rest.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.Operation;

@Service
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class SaldoService {
	private AccountService accountService;
	private OperationService operationService;
	
	private Map<Long, BigDecimal> accountsSaldos;
	
	public SaldoService(AccountService accountService, OperationService operationService) {
		this.accountService = accountService;
		this.operationService = operationService;
		
		accountsSaldos = new HashMap<>();
	}
	
	public BigDecimal calculateFundsForAccount(Long accountId) throws Exception {
		if(accountsSaldos.containsKey(accountId)) {
			return accountsSaldos.get(accountId);
		}
		
		BigDecimal funds = new BigDecimal(0); 
		Account account = accountService.getOne(accountId);
		List<Operation> operations = operationService.getAllForAccount(accountId);
		for(Operation operation : operations) {
			funds = funds.add(operation.getFunds());
		}
	
		for(Account childAccount : account.getChildAccounts()) {
			BigDecimal childFunds = this.calculateFundsForAccount(childAccount.getId());
			funds = funds.add(childFunds);
		}
		
		accountsSaldos.put(accountId, funds);
		return funds;
	}
}
