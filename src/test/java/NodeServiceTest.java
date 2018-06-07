import application.SpringBootWebApplication;
import application.entity.Node;
import application.service.MindMapService;
import application.service.NodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class NodeServiceTest {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private MindMapService mindMapService;

    @Test
    public void testAdd() {
        nodeService.deleteAll();
        nodeService.addRootNode(mindMapService.getByName("map1").getId(), "rootNode");
        Node root = nodeService.getByName("rootNode");
        Node node1 = new Node();
        node1.setName("child1_1");
        Node node2 = new Node();
        node2.setName("child1_2");
        Node node3 = new Node();
        node3.setName("child2_1");
        Node node4 = new Node();
        node4.setName("child3_1");
        node3.addChild(node4);
        node1.addChild(node3);
        root.addChild(node1);
        root.addChild(node2);
        nodeService.update(root);

    }

    @Test
    public void testGetAll() {
        List<Node> nodes = nodeService.getAll(167);
        Node root = nodes.get(0);
        assertEquals(root.getChildNodes().size(), 2);
    }

    @Test
    public void testGetRootByMap() {
        Node root = nodeService.getRootNodeByMindMapId(167);
        assertEquals(root.getName(), "rootNode");
    }

    @Test
    public void testGetByFather() {
        Set<Node> nodes = nodeService.getByFatherNodeId(123);
        assertEquals(nodes.size(), 2);
    }

    @Test
    public void testGetByChild() {
        Node node = nodeService.getByChildNodeId(156);
        assertEquals(node.getName(), "child2_1");
    }

}
