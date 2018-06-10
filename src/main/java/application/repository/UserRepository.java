package application.repository;

import application.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    User findById(long id);

    Optional<User> findByEmail(String email);

    void deleteById(long id);
}
