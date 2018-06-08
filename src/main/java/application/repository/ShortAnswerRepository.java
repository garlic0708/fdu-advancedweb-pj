package application.repository;

import application.entity.ShortAnswerQuestion;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface ShortAnswerRepository extends CrudRepository<ShortAnswerQuestion, Long> {
    ShortAnswerQuestion findById(long id);

    @Query("MATCH (n:Node)-[:hasHomeWork]->(q:ShortAnswerQuestion) WHERE ID(n)={0}" +
            "RETURN q")
    Set<ShortAnswerQuestion> findByFatherNode_Id(long id);

    void deleteById(long id);
}
