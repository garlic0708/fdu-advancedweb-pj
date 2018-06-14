package application.service;

import application.entity.MindMap;
import application.entity.MindMapManipulation;

import java.util.List;
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

    MindMap addMindMap(long courseId, String name);

    void deleteMindMap(long id);

    MindMap updateMindMap(MindMap mindMap);

    void manipulate(long id, List<MindMapManipulation> manipulations);
}
