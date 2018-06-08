package application.repository;

import application.entity.Resource;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    Resource findById(long id);

    Resource findByName(String name);

    @Query("MATCH (n:Node)-[:hasResource]->(q:Resource) WHERE ID(n)={0}" +
            "RETURN q")
    Resource findByFatherNode_Id(long nodeId);

    void deleteById(long id);
}
