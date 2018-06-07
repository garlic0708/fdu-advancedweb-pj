package application.repository;

import application.entity.StudentAnswerForMultipleChoice;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/2.
 */
public interface StudentAnswerForMultipleChoiceRepository
        extends CrudRepository<StudentAnswerForMultipleChoice, Long> {
    StudentAnswerForMultipleChoice findById(long id);
}
