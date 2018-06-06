package application.repository;

import application.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);
    User findById(long id);
    void deleteById(long id);
}
