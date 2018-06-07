package application.repository;

import application.entity.Node;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface NodeRepository extends CrudRepository<Node, Long> {
    Node findById(long id);

    Node findByName(String name);

    @Query("MATCH (map: MindMap)-[:hasRootNode]->(root:Node)-[rel:hasChild*]->(child:Node)" +
            "WHERE ID(map) = {0} RETURN root, rel, child")
    List<Node> findByMindMapId(long id);
}
