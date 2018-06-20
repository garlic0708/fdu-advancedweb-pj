package application.service.impl;

import application.entity.Course;
import application.entity.Courseware;
import application.entity.Node;
import application.repository.CoursewareRepository;
import application.repository.NodeRepository;
import application.service.CoursewareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/7.
 */
@Service
public class CoursewareServiceImpl implements CoursewareService {
    private final CoursewareRepository coursewareRepository;
    private final NodeRepository nodeRepository;

    @Autowired
    public CoursewareServiceImpl(CoursewareRepository coursewareRepository, NodeRepository nodeRepository) {
        this.coursewareRepository = coursewareRepository;
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Courseware getById(long id) {
        return coursewareRepository.findById(id);
    }

    @Override
    public Courseware getByName(String name) {
        return coursewareRepository.findByName(name);
    }

    @Override
    public Set<Courseware> getByNodeId(long nodeId) {
        Node node = nodeRepository.findById(nodeId);
        Set<Courseware> coursewareSet = new HashSet<>();
        for (Courseware courseware: node.getCoursewares()) {
            coursewareSet.add(coursewareRepository.findById(courseware.getId().longValue()));
        }
        return coursewareSet;
    }

    @Override
    public Courseware addCourseware(long nodeId, String name, String location) {
        Courseware courseware = new Courseware();
        courseware.setName(name);
        courseware.setFileLocation(location);

        Node node = nodeRepository.findById(nodeId);
        courseware.setFatherNode(node);
        return coursewareRepository.save(courseware);
    }

    @Override
    public void deleteCourseware(long id) {
        coursewareRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        coursewareRepository.deleteAll();
    }

    @Override
    public void updateCourseware(Courseware courseware) {
        coursewareRepository.save(courseware);
    }

    @Override
    public String getFilePath(long id) {
        Courseware courseware = getById(id);
        return courseware.getFileLocation() + courseware.getName();
    }

}
