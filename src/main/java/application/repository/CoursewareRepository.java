package application.repository;

import application.entity.Courseware;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface CoursewareRepository extends CrudRepository<Courseware, Long> {
    Courseware findById(long id);

    Courseware findByName(String name);

    void deleteById(long id);
}
