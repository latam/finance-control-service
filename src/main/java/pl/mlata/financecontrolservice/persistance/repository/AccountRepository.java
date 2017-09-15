package pl.mlata.financecontrolservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.mlata.financecontrolservice.persistance.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
