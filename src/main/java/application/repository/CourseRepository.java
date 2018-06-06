package application.repository;

import application.entity.Course;
import application.entity.MindMap;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findById(long id);
    Course findByName(String name);
    Course findByMapsContains(MindMap map);
    void deleteById(long id);
}
