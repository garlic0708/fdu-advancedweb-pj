package application.repository;

import application.entity.MutipleChoiceQuestion;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface MutipleChoiceRepository extends CrudRepository<MutipleChoiceQuestion, Long> {
    MutipleChoiceQuestion findById(long id);
}
