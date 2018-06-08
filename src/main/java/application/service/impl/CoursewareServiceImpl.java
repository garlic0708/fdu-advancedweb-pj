package application.service.impl;

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
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/7.
 */
@Service
public class CoursewareServiceImpl implements CoursewareService {
    @Autowired
    private CoursewareRepository coursewareRepository;
    @Autowired
    private NodeRepository nodeRepository;
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
        return null;
    }

    @Override
    public void addCourseware(long nodeId, String name, String location) {
        Courseware courseware = new Courseware();
        courseware.setName(name);
        courseware.setFileLocation(location);

        Node node = nodeRepository.findById(nodeId);
        courseware.setFatherNode(node);
        coursewareRepository.save(courseware);
    }

    @Override
    public void deleteCourseware(long id) {
        coursewareRepository.deleteById(id);
    }

    @Override
    public void updateCourseware(Courseware courseware) {
        coursewareRepository.save(courseware);
    }

    @Override
    public void uploadFile(byte[] file, String filePath, String fileName) throws IOException {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
