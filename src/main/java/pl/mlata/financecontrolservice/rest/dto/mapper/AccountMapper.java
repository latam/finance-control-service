package pl.mlata.financecontrolservice.rest.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.rest.dto.AccountDto;
import pl.mlata.financecontrolservice.rest.service.AccountService;
import pl.mlata.financecontrolservice.rest.service.SaldoHelper;
import pl.mlata.financecontrolservice.rest.service.SaldoService;
import pl.mlata.financecontrolservice.rest.service.UserService;

@Component
public class AccountMapper implements DataObjectMapper<Account, AccountDto> {
	private UserService userService;
	private SaldoHelper saldoHelper;
	private ModelMapper modelMapper;

	public AccountMapper(UserService userService, SaldoHelper saldoHelper) {
		this.userService = userService;
		this.saldoHelper = saldoHelper;

		modelMapper = new ModelMapper();
	}

	@Override
	public Account mapTo(AccountDto accountDto) throws Exception {
		Account account = modelMapper.map(accountDto, Account.class);
		Account parentAccount = null;
		account.setParentAccount(parentAccount);

		account.setUser(userService.getCurrentUser());
		return account;
	}

	@Override
	public AccountDto mapFrom(Account account) throws Exception {
		AccountDto accountDto = modelMapper.map(account, AccountDto.class);
		List<AccountDto> childrenAccountsDtos = new ArrayList<>();
		for(Account childAccount : account.getChildAccounts()) {
			childrenAccountsDtos.add(mapFrom(childAccount));
		}
		accountDto.setFunds(saldoHelper.calculateFundsForAccount(account));
		accountDto.setChildAccounts(childrenAccountsDtos);
		return accountDto;
	}
}
