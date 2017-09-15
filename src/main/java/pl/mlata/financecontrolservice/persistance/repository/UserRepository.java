package pl.mlata.financecontrolservice.persistance.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import pl.mlata.financecontrolservice.persistance.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
