package application.service.impl;

import application.entity.MindMap;
import application.entity.Node;
import application.entity.view.DescriptionView;
import application.entity.view.NodeAttachments;
import application.entity.view.TypeDescriptionView;
import application.repository.*;
import application.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class NodeServiceImpl implements NodeService {
    private final NodeRepository nodeRepository;
    private final MindMapRepository mindMapRepository;
    private final HomeWorkRepository homeWorkRepository;
    private final CoursewareRepository coursewareRepository;
    private final ResourceRepository resourceRepository;

    @Autowired
    public NodeServiceImpl(NodeRepository nodeRepository, MindMapRepository mindMapRepository, HomeWorkRepository homeWorkRepository, CoursewareRepository coursewareRepository, ResourceRepository resourceRepository) {
        this.nodeRepository = nodeRepository;
        this.mindMapRepository = mindMapRepository;
        this.homeWorkRepository = homeWorkRepository;
        this.coursewareRepository = coursewareRepository;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Node getById(long id) {
        return nodeRepository.findById(id);
    }

    @Override
    public Node getByName(String name) {
        return nodeRepository.findByName(name);
    }

    @Override
    public Set<Node> getByFatherNodeId(long fatherId) {
        return nodeRepository.findById(fatherId).getChildNodes();
    }

    @Override
    public Node getByChildNodeId(long childId) {
        Node child = nodeRepository.findById(childId);
        return nodeRepository.findById(child.getFatherNode().getId().longValue());
    }

    @Override
    public Node getRootNodeByMindMapId(long mindMapId) {
        MindMap mindMap = mindMapRepository.findById(mindMapId);
        return nodeRepository.findById(mindMap.getRootNode().getId().longValue());
    }

    @Override
    public Node getAll(long mindMapId) {
        return nodeRepository.findByMindMapId(mindMapId).get(0);
    }

    @Override
    public Node addRootNode(long mindMapId, String nodeName) {
        MindMap map = mindMapRepository.findById(mindMapId);
        Node root = new Node();
        root.setName(nodeName);
        root.setFatherMap(map);
        return nodeRepository.save(root);
    }

    @Override
    public Node addNode(long fatherNodeId, String childName) {
        Node father = nodeRepository.findById(fatherNodeId);
        Node child = new Node();
        child.setName(childName);
        child.setFatherNode(father);
        return nodeRepository.save(child);
    }

    @Override
    public void deleteNode(long id) {
        nodeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        nodeRepository.deleteAll();
    }

    @Override
    public void update(Node node) {
        nodeRepository.save(node);
    }

    @Override
    public NodeAttachments getAttachments(long id) {
        Set<TypeDescriptionView> questions = homeWorkRepository.findByFatherNode_Id(id);
        Set<DescriptionView> coursewares = coursewareRepository.findByFatherNode_Id(id);
        Set<TypeDescriptionView> resources = resourceRepository.findByFatherNode_Id(id);
        return new NodeAttachments(questions, resources, coursewares);
    }
}
