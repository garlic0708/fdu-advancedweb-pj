package application.service;

import application.entity.Node;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface NodeService {
    Node getById(long id);

    Node getByName(String name);

    Set<Node> getByFatherNodeId(long fatherId);

    Node getByChildNodeId(long childId);

    Node getRootNodeByMindMapId(long mindMapId);

    Node getAll(long mindMapId);

    void addRootNode(long mindMapId, String nodeName);

    void addNode(long fatherNodeId, String childName);

    void deleteNode(long id);
}
