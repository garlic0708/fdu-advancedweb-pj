package application.repository;

import application.entity.StudentAnswerForShortAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/2.
 */
public interface StudentAnswerForShortAnswerRepository
        extends CrudRepository<StudentAnswerForShortAnswer, Long>{
    StudentAnswerForShortAnswer findById(long id);

    Set<StudentAnswerForShortAnswer> findByQuestion_Id(long questionId);

    Set<StudentAnswerForShortAnswer> findByStudent_Id(long studentId);
}
