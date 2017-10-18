package pl.mlata.financecontrolservice.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.mlata.financecontrolservice.exception.ResourceNotFoundException;
import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.persistance.repository.AccountRepository;
import pl.mlata.financecontrolservice.rest.dto.AccountDto;
import pl.mlata.financecontrolservice.rest.dto.mapper.AccountMapper;

@Service
public class AccountService {
	private UserService userService;
	private AccountMapper accountMapper;
	private AccountRepository accountRepository;

	public AccountService(UserService userService, AccountMapper accountMapper, AccountRepository accountRepository) {
		this.userService = userService;
		this.accountMapper = accountMapper;
		this.accountRepository = accountRepository;
	}

	public List<AccountDto> getRootAccounts() throws Exception {
		List<Account> accounts = accountRepository.findByParentAccountIsNullAndUser(getCurrentUser());
		List<AccountDto> accountDtos = new ArrayList<>();
		for(Account account : accounts) {
			accountDtos.add(accountMapper.mapFrom(account));
		}

		return accountDtos;
	}
	
	public AccountDto getOne(Long id) throws Exception {
		Account account = accountRepository.findOne(id);
		if(account == null) {
			throw new ResourceNotFoundException(Account.class.getName() + "by id: " + id + " was not found.");
		}
		return accountMapper.mapFrom(account);
	}
	
	public AccountDto saveOrUpdate(AccountDto accountDto) throws Exception {
		Account account = accountMapper.mapTo(accountDto);
		account = accountRepository.save(account);
		account.getParentAccount().getChildAccounts().add(account);
		accountRepository.save(account.getParentAccount());
		
		return accountMapper.mapFrom(account);
	}
	
	private User getCurrentUser() {
		return userService.getCurrentUser();
	}
}
