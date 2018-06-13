package application.repository;

import application.entity.HomeWork;
import application.entity.view.TypeDescriptionView;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface HomeWorkRepository extends CrudRepository<HomeWork, Long> {
    HomeWork findById(long id);

    @Query("MATCH (n:Node)-[:hasHomeWork]->(q:HomeWork) WHERE ID(n)={0}" +
            "RETURN q")
    Set<TypeDescriptionView> findByFatherNode_Id(long id);

    void deleteById(long id);

    @Query("MATCH (m:MindMap)-[:hasRootNode]->(n:Node)-[:hasChild*]->(c:Node)," +
            "(x:Node)-[:hasHomeWork]->(q:HomeWork) WHERE ID(m)={0} AND x IN [n, c]" +
            "RETURN q")
    Set<HomeWork> findByMindMapId(long id);


}
