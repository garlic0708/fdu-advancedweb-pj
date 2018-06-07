package application.service;

import application.entity.Course;
import application.entity.MindMap;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface MindMapService {
    MindMap getById(long id);

    MindMap getByName(String name);

    MindMap getByRootNodeId(long nodeId);

    Set<MindMap> getByCourseId(long courseId);

    Set<MindMap> getByTeacherId(long teacherId);

    void addMindMap(long courseId, String name);

    void deleteMindMap(long id);

    void updateMindMap(MindMap mindMap);
}
