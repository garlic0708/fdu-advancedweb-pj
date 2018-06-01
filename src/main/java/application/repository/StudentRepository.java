package application.repository;

import application.entity.Student;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface StudentRepository extends CrudRepository<Student, Long> {
}
