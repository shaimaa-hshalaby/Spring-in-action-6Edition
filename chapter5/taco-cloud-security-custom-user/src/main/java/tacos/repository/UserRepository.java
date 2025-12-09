package tacos.repository;

import org.springframework.data.repository.CrudRepository;

import tacos.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);

}
