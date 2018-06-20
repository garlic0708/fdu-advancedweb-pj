package application.service;

import application.entity.Courseware;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface CoursewareService {
    Courseware getById(long id);

    Courseware getByName(String name);

    Set<Courseware> getByNodeId(long nodeId);

    Courseware addCourseware(long nodeId, String name, String location);

    void deleteCourseware(long id);

    void deleteAll();

    void updateCourseware(Courseware courseware);

    String getFilePath(long id);
}
