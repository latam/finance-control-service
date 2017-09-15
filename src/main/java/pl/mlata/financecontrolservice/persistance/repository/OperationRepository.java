package pl.mlata.financecontrolservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.mlata.financecontrolservice.persistance.model.Operation;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {

}
