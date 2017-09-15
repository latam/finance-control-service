package pl.mlata.financecontrolservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import com.jayway.jsonpath.internal.path.ArraySliceOperation.Operation;

public interface OperationRepository extends CrudRepository<Operation, Long> {

}
