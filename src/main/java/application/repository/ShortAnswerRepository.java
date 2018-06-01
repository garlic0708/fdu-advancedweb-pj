package application.repository;

import application.entity.ShortAnswerQuestion;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface ShortAnswerRepository extends CrudRepository<ShortAnswerQuestion, Long> {
}
