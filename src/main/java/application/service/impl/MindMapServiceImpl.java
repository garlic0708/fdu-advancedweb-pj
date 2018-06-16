package application.service.impl;

import application.entity.*;
import application.repository.CourseRepository;
import application.repository.MindMapRepository;
import application.repository.NodeRepository;
import application.repository.TeacherRepository;
import application.service.MindMapService;
import application.service.util.ListCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class MindMapServiceImpl implements MindMapService {
    private final MindMapRepository mindMapRepository;
    private final NodeRepository nodeRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public MindMapServiceImpl(MindMapRepository mindMapRepository, NodeRepository nodeRepository, TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.mindMapRepository = mindMapRepository;
        this.nodeRepository = nodeRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

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

    @Override
    public Set<MindMap> getByTeacherId(long teacherId) {
        Set<MindMap> maps = new HashSet<>();
        Teacher teacher = teacherRepository.findById(teacherId);
        Set<Course> courseSet = teacher.getCourses();
        for (Course course : courseSet) {
            Long id = course.getId();
            course = courseRepository.findById(id).orElse(null);
            if (course != null)
                maps.addAll(course.getMaps());
        }
        return maps;
    }

    @Override
    public MindMap addMindMap(long courseId, String name) {
        Course course = courseRepository.findById(courseId);
        MindMap mindMap = new MindMap();
        mindMap.setName(name);
        mindMap.setCourse(course);
        return mindMapRepository.save(mindMap);
    }

    @Override
    public void deleteMindMap(long id) {
        mindMapRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        mindMapRepository.deleteAll();
    }

    @Override
    public MindMap updateMindMap(MindMap mindMap) {
        return mindMapRepository.save(mindMap);
    }

    @Override
    public void manipulate(long id, List<MindMapManipulation> manipulations) {
        List<Node> nodes = nodeRepository.findByMindMapId(id);
        ListCacheUtil<Integer, Node> cacheUtil = new ListCacheUtil<>(nodes,
                (i, t) -> t.getInternalId() == i);
        List<Node> nodesToRemove = new ArrayList<>();
        loop:
        for (MindMapManipulation manipulation : manipulations) {
            Node node, root;
            switch (manipulation.getAction()) {
                case "addNode":
                    node = new Node(manipulation.getId());
                    root = cacheUtil.getItem(manipulation.getParentId());
                    cacheUtil.addItem(manipulation.getId(), node);
                    root.getChildNodes().add(node);
                    break;
                case "removeNode":
                    node = cacheUtil.getItem(manipulation.getId());
                    if ((root = node.getFatherNode()) == null)
                        continue loop;
                    Set<Node> childNodes = root.getChildNodes();
                    childNodes.addAll(node.getChildNodes());
                    childNodes.remove(node);
                    cacheUtil.removeItem(manipulation.getId());
                    nodesToRemove.add(node);
                    break;
                case "changeName":
                    cacheUtil.getItem(manipulation.getId()).setName(manipulation.getValue());
                    break;
                case "changeColor":
                    cacheUtil.getItem(manipulation.getId()).setColor(manipulation.getValue());
            }
        }
        nodeRepository.deleteAll(nodesToRemove);
        nodeRepository.save(nodes.get(0));
    }
}
