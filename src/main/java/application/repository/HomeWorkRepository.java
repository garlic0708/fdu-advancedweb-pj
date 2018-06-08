package application.repository;

import application.entity.HomeWork;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface HomeWorkRepository extends CrudRepository<HomeWork, Long> {
    HomeWork findById(long id);

    Set<HomeWork> findByFatherNode_Id(long id);

    void deleteById(long id);

    @Query("MATCH (n:Node)-[:hasHomeWork]->(q:HomeWork) WHERE ID(n)={0}" +
            "RETURN q")
    Set<HomeWork> findByMindMapId(long id);

}
