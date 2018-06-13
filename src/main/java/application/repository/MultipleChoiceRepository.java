package application.repository;

import application.entity.MultipleChoiceQuestion;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Map;
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

    @Query("MATCH (q:MultipleChoiceQuestion)<-[r:resolve]-(s:Student) WHERE ID(q) = {0}" +
            "RETURN DISTINCT r.answer AS answer, COUNT(s) AS count")
    Iterable<Map<String, Object>> findAnswersToQuestion(long id);

    @Query("MATCH (s:Student)-[:`Choose Course`]->(m:MindMap)-[:hasRootNode]->(n:Node)-[:hasChild*]->(c:Node)," +
            "(x:Node)-[:hasHomeWork]->(q:HomeWork) WHERE id(q)={0} AND x IN [n, c]" +
            "RETURN COUNT(s)")
    long getChooseCourseCount(long id);
}
