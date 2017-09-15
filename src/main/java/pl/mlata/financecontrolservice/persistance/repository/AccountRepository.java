package pl.mlata.financecontrolservice.persistance.repository;

import org.springframework.data.repository.CrudRepository;

import pl.mlata.financecontrolservice.persistance.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

}
