package application.repository;

import application.entity.Course;
import application.entity.MindMap;
import org.springframework.data.repository.CrudRepository;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public interface MindMapRepository extends CrudRepository<MindMap, Long> {
    MindMap findByName(String name);
    MindMap findById(long id);
}
