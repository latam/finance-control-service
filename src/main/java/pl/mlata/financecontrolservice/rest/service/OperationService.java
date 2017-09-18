package pl.mlata.financecontrolservice.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.mlata.financecontrolservice.persistance.model.Operation;
import pl.mlata.financecontrolservice.persistance.model.User;
import pl.mlata.financecontrolservice.persistance.repository.OperationRepository;

@Service
public class OperationService {
	private UserService userService;
	private OperationRepository operationRepository;
	
	public OperationService(UserService userService, OperationRepository operationRepository) {
		this.userService = userService;
		this.operationRepository = operationRepository;
	}

	public List<Operation> getAll() throws Exception {
		
		return operationRepository.findByUser(getCurrentUser());
	}
	
	public List<Operation> getAllForAccount(Long accountId) {
		return operationRepository.findByToAccountId(accountId);
	}
	
	@Transactional
	public Operation saveOperation(Operation operation) throws Exception {
		return operationRepository.save(operation);
	}
	
	private User getCurrentUser() {
		return userService.getCurrentUser();
	}
}
