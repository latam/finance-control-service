package pl.mlata.financecontrolservice.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.rest.dto.AccountData;
import pl.mlata.financecontrolservice.rest.dto.mapper.AccountMapper;
import pl.mlata.financecontrolservice.rest.service.AccountService;
import pl.mlata.financecontrolservice.rest.service.SaldoService;

@RestController
@RequestMapping(value="/account")
public class AccountController {
	private AccountService accountService;
	private SaldoService saldoService;
	private AccountMapper accountMapper;

	public AccountController(AccountService accountService, SaldoService saldoService, AccountMapper accountMapper) {
		this.accountService = accountService;
		this.saldoService = saldoService;
		this.accountMapper = accountMapper;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AccountData>> getAccounts() throws Exception {
		List<AccountData> accountsData = new ArrayList<>();
		for(Account account : accountService.getAll()) {
			AccountData accountData = accountMapper.mapFrom(account);
			accountData.setFunds(saldoService.calculateFundsForAccount(account.getId()));
			accountsData.add(accountData);
		}
		
		return new ResponseEntity<>(accountsData, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<AccountData> saveAccount(@RequestBody AccountData accountData) throws Exception {
		Account account = accountMapper.mapTo(accountData);
		account = accountService.saveOrUpdate(account);
		
		accountData = accountMapper.mapFrom(account);
		return new ResponseEntity<>(accountData, HttpStatus.OK);
	}
}
