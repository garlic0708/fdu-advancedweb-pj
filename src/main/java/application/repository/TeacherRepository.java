package application.repository;

import application.entity.Teacher;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Teacher findByName(String name);

    Teacher findById(long id);
}
