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

    @Query("MATCH (map: MindMap)-[:hasRootNode]->(root:Node)-[:hasChild*]->(child:Node)," +
            "(hw: HomeWork)<-[:hasHomeWork]-(n) WHERE n IN [root, child] AND ID(map) = {0} " +
            "RETURN hw")
    Set<HomeWork> findByMindMapId(long id);
}
