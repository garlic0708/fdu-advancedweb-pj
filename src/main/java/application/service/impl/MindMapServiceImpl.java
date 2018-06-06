package application.service.impl;

import application.entity.Course;
import application.entity.MindMap;
import application.entity.Node;
import application.entity.Teacher;
import application.repository.CourseRepository;
import application.repository.MindMapRepository;
import application.repository.NodeRepository;
import application.repository.TeacherRepository;
import application.service.MindMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class MindMapServiceImpl implements MindMapService {
    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public MindMap getById(long id) {
        return mindMapRepository.findById(id);
    }

    @Override
    public MindMap getByName(String name) {
        return mindMapRepository.findByName(name);
    }

    @Override
    public MindMap getByRootNodeId(long nodeId) {
        Node node = nodeRepository.findById(nodeId);
        return node.getFatherMap();
    }

    @Override
    public Set<MindMap> getByCourseId(long courseId) {
        Course course = courseRepository.findById(courseId);
        return course.getMaps();
    }

    //需要测试
    @Override
    public Set<MindMap> getByTeacherId(long teacherId) {
        Set<MindMap> maps = new HashSet<>();
        Teacher teacher = teacherRepository.findById(teacherId);
        Set<Course> courseSet = teacher.getCourses();
        while (courseSet.iterator().hasNext()) {
            Course course = courseSet.iterator().next();
            maps.addAll(course.getMaps());
        }
        return maps;
    }
    //需要测试
    @Override
    public void addMindMap(long courseId, String name) {
        Course course = courseRepository.findById(courseId);
        MindMap mindMap = new MindMap();
        mindMap.setName(name);
        course.addMap(mindMap);
        courseRepository.save(course);
    }
    //需要测试
    @Override
    public void deleteMindMap(long id) {
        mindMapRepository.deleteById(id);
    }

    @Override
    public void updateMindMap(MindMap mindMap) {
        mindMapRepository.save(mindMap);
    }
}
