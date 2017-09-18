package pl.mlata.financecontrolservice.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.mlata.financecontrolservice.exception.ResourceNotFoundException;
import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.persistance.repository.AccountRepository;

@Service
public class AccountService {
	private UserService userService;
	private AccountRepository accountRepository;
	
	public AccountService(UserService userService, AccountRepository accountRepository) {
		this.userService = userService;
		this.accountRepository = accountRepository;
	}

	public List<Account> getAll() throws Exception {
		return accountRepository.findByUser(getCurrentUser());
	}
	
	public List<Account> getMultiple(List<Long> ids) {
		return accountRepository.findByIdInAndUser(ids, getCurrentUser());
	}
	
	public List<Account> getRootAccounts() {
		return accountRepository.findByParentAccountIsNullAndUser(getCurrentUser());
	}
	
	public Account getOne(Long id) throws ResourceNotFoundException {
		Account account = accountRepository.findOne(id);
		if(account == null) {
			throw new ResourceNotFoundException(Account.class.getName() + "by id: " + id + " was not found.");
		}
		return account;
	}
	
	public Account saveOrUpdate(Account account) {
		account = accountRepository.save(account);
		account.getParentAccount().getChildAccounts().add(account);
		accountRepository.save(account.getParentAccount());
		
		return account;
	}
	
	private User getCurrentUser() {
		return userService.getCurrentUser();
	}
}
