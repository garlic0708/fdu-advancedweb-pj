package application.repository;

import application.entity.Course;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
}
