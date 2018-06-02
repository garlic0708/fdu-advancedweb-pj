package application.repository;

import application.entity.StudentAnswerForShortAnswer;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/2.
 */
public interface StudentAnswerForShortAnswerRepository
        extends CrudRepository<StudentAnswerForShortAnswer, Long>{
    StudentAnswerForShortAnswer findById(long id);
}
