package pl.mlata.financecontrolservice.persistance.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.mlata.financecontrolservice.persistance.model.Operation;
import pl.mlata.financecontrolservice.persistance.model.User;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
	List<Operation> findByUser(User user);
	List<Operation> findByToAccountId(Long accountId);
}
