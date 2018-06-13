package application.service.impl;

import application.entity.MindMap;
import application.entity.Node;
import application.repository.MindMapRepository;
import application.repository.NodeRepository;
import application.service.MindMapService;
import application.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private MindMapRepository mindMapRepository;
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
    public void addRootNode(long mindMapId, String nodeName) {
        MindMap map = mindMapRepository.findById(mindMapId);
        Node root = new Node();
        root.setName(nodeName);
        map.setRootNode(root);
        mindMapRepository.save(map);
    }

    @Override
    public void addNode(long fatherNodeId, String childName) {
        Node father = nodeRepository.findById(fatherNodeId);
        Node child = new Node();
        child.setName(childName);
        father.addChild(child);
        nodeRepository.save(father);
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
}
