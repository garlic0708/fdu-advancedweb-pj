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

    @Query("MATCH (map: MindMap)-[:hasRootNode]->(root:Node) WHERE ID(map) = {0} " +
            "OPTIONAL MATCH (root)-[rel:hasChild*]->(child:Node) RETURN root, rel, child")
    List<Node> findByMindMapId(long id);

    void deleteById(long id);

}
