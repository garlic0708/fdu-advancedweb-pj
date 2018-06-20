package application.repository;

import application.entity.Course;
import application.entity.MindMap;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findById(long id);

    Course findByName(String name);

    Course findByMapsContains(MindMap map);

    void deleteById(long id);

    @Query("MATCH (n:Course) WHERE n.name =~ {0} RETURN n")
    Set<Course> findByNameLike(String query);

    @Query("MATCH (a:Course)<-[r:`Select Course`]-(b:Student)-[r1:resolve]->(x:HomeWork)<-[:hasHomeWork]-(n:Node), " +
            "(a)-[:hasMap]->(m:MindMap)-[:hasRootNode]->(n0:Node)-[:hasChild*]->(n1:Node) " +
            "WHERE id(b)={0} AND id(a)={1} AND n IN [n0, n1] DELETE r, r1")
    void deselectCourse(long studentId, long courseId);
}
