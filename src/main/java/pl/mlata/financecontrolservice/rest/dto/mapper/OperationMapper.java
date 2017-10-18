package pl.mlata.financecontrolservice.rest.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.Operation;
import pl.mlata.financecontrolservice.persistance.repository.AccountRepository;
import pl.mlata.financecontrolservice.rest.dto.OperationData;
import pl.mlata.financecontrolservice.rest.service.AccountService;
import pl.mlata.financecontrolservice.rest.service.UserService;

@Component
public class OperationMapper implements DataObjectMapper<Operation, OperationData> {
	private UserService userService;
	private AccountRepository accountRepository;
	private ModelMapper modelMapper;
	
	public OperationMapper(AccountRepository accountRepository, UserService userService) {
		this.accountRepository = accountRepository;
		this.userService = userService;
		
		modelMapper = new ModelMapper();
	}

	@Override
	public Operation mapTo(OperationData operationData) throws Exception {
		Operation operation = modelMapper.map(operationData, Operation.class);
		Account toAccount = accountRepository.findOne(operationData.getToAccountId());
		Account fromAccount = null;
		if(operationData.getFromAccountId() != null) {
			fromAccount = accountRepository.findOne(operationData.getFromAccountId());
		}
		
		operation.setUser(userService.getCurrentUser());
		operation.setToAccount(toAccount);
		operation.setFromAccount(fromAccount);
		
		return operation;
	}

	@Override
	public OperationData mapFrom(Operation operation) throws Exception {
		return modelMapper.map(operation, OperationData.class);
	}

}
