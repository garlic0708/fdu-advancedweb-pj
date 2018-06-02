package application.repository;

import application.entity.Node;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface NodeRepository extends CrudRepository<Node, Long> {
    Node findById(long id);

    Node findByName(String name);
}
