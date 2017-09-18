package pl.mlata.financecontrolservice.persistance.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.mlata.financecontrolservice.persistance.model.Account;
import pl.mlata.financecontrolservice.persistance.model.User;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	List<Account> findByUser(User user);
	List<Account> findByIdInAndUser(List<Long> ids, User user);
	List<Account> findByParentAccountIsNullAndUser(User user);
}
