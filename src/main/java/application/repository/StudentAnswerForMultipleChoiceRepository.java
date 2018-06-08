package application.repository;

import application.entity.StudentAnswerForMultipleChoice;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/2.
 */
public interface StudentAnswerForMultipleChoiceRepository
        extends CrudRepository<StudentAnswerForMultipleChoice, Long> {
    StudentAnswerForMultipleChoice findById(long id);

    @Query("MATCH (n:MultipleChoiceQuestion)-[:resolve]->(q:StudentAnswerForMultipleChoice) WHERE ID(n)={0}" +
            "RETURN q")
    Set<StudentAnswerForMultipleChoice> findByQuestion_Id(long questionId);

    @Query("MATCH (n:Student)-[:resolve]->(q:StudentAnswerForMultipleChoice) WHERE ID(n)={0}" +
            "RETURN q")
    Set<StudentAnswerForMultipleChoice> findByStudent_Id(long studentId);
}
