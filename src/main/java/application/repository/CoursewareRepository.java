package application.repository;

import application.entity.Courseware;
import application.entity.view.DescriptionView;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface CoursewareRepository extends CrudRepository<Courseware, Long> {
    Courseware findById(long id);

    Courseware findByName(String name);

    void deleteById(long id);

    @Query("MATCH (n:Node)-[:hasHomeWork]->(q:Courseware) WHERE ID(n)={0}" +
            "RETURN q")
    Set<DescriptionView> findByFatherNode_Id(long id);
}
