package application.repository;

import application.entity.StudentAnswerForMutipleChoice;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/2.
 */
public interface StudentAnswerForMutipleChoiceRepository
        extends CrudRepository<StudentAnswerForMutipleChoice, Long> {
    StudentAnswerForMutipleChoice findById(long id);
}
