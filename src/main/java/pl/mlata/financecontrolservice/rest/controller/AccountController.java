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
import pl.mlata.financecontrolservice.rest.dto.AccountDto;
import pl.mlata.financecontrolservice.rest.dto.mapper.AccountMapper;
import pl.mlata.financecontrolservice.rest.service.AccountService;
import pl.mlata.financecontrolservice.rest.service.SaldoService;

@RestController
@RequestMapping(value="/account")
public class AccountController {
	private AccountService accountService;
	private AccountMapper accountMapper;

	public AccountController(AccountService accountService, AccountMapper accountMapper) {
		this.accountService = accountService;
		this.accountMapper = accountMapper;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AccountDto>> getAccounts() throws Exception {
		return new ResponseEntity<>(accountService.getRootAccounts(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountDto) throws Exception {
		return new ResponseEntity<>(accountService.saveOrUpdate(accountDto), HttpStatus.OK);
	}
}
