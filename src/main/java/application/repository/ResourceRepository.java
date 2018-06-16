package application.repository;

import application.entity.Resource;
import application.entity.view.TypeDescriptionView;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    Resource findById(long id);

    Resource findByName(String name);

    @Query("MATCH (n:Node)-[:hasResource]->(q:Resource) WHERE ID(n)={0}" +
            "RETURN q")
    Set<TypeDescriptionView> findByFatherNode_Id(long nodeId);

    Set<Resource> findByFatherNode_Name(String name);

    void deleteById(long id);
}
