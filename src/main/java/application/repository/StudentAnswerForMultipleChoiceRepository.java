package application.repository;

import application.entity.StudentAnswerForMultipleChoice;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/2.
 */
public interface StudentAnswerForMultipleChoiceRepository
        extends CrudRepository<StudentAnswerForMultipleChoice, Long> {
    StudentAnswerForMultipleChoice findById(long id);

    Set<StudentAnswerForMultipleChoice> findByQuestion_Id(long questionId);

    Set<StudentAnswerForMultipleChoice> findByStudent_Id(long studentId);
}
