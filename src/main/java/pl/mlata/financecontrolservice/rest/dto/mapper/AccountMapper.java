package pl.mlata.financecontrolservice.rest.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.rest.dto.AccountData;
import pl.mlata.financecontrolservice.rest.service.AccountService;
import pl.mlata.financecontrolservice.rest.service.UserService;

@Component
public class AccountMapper implements DataObjectMapper<Account, AccountData> {
	private AccountService accountService;
	private UserService userService;
	private ModelMapper modelMapper;
	
	public AccountMapper(AccountService accountService, UserService userService) {
		this.accountService = accountService;
		this.userService = userService;
		
		modelMapper = new ModelMapper();
	}

	@Override
	public Account mapTo(AccountData accountData) throws Exception {
		Account account = modelMapper.map(accountData, Account.class);
		Account parentAccount = null;
		if(accountData.getParentId() != null) {
			parentAccount = accountService.getOne(accountData.getParentId());
		}
		account.setParentAccount(parentAccount);
		
		List<Account> childAccounts = accountService.getMultiple(accountData.getChildAccountsId());
		account.setChildAccounts(childAccounts);
		account.setUser(userService.getCurrentUser());
		return account;
	}

	@Override
	public AccountData mapFrom(Account account) throws Exception {
		AccountData accountData = modelMapper.map(account, AccountData.class);
		
		List<Long> childrenIds = account.getChildAccounts().stream()
				.map(Account::getId)
				.collect(Collectors.toList());
		accountData.setChildAccountsId(childrenIds);
		if(account.getParentAccount() != null) {
			accountData.setParentId(account.getParentAccount().getId());
		}
		
		return accountData;
	}
}
