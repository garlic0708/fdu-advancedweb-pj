package application.repository;

import application.entity.MultipleChoiceQuestion;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface MultipleChoiceRepository extends CrudRepository<MultipleChoiceQuestion, Long> {
    MultipleChoiceQuestion findById(long id);

    @Query("MATCH (n:Node)-[:hasHomeWork]->(q:MultipleChoiceQuestion) WHERE ID(n)={0}" +
            "RETURN q")
    Set<MultipleChoiceQuestion> findByFatherNode_Id(long id);
}
