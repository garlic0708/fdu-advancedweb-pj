package application.service;

import application.entity.Courseware;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface CoursewareService {
    Courseware getById(long id);

    Courseware getByName(String name);

    Set<Courseware> getByNodeId(long nodeId);

    void addResource(String name, String location);

    void deleteCourseware(long id);

    void updateResource(Courseware resource);
}
