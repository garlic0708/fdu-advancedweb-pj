package application.repository;

import application.entity.Course;
import application.entity.CourseStudents;
import application.entity.MindMap;
import org.springframework.data.neo4j.annotation.Query;
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

    @Query("MATCH (c: Course) WHERE ID(c) = {0} return c")
    CourseStudents findStudentsByCourseId(long id);
}
