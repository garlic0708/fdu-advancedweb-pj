package application.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
@NodeEntity
public class MindMap {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Relationship(type = "hasRootNode")
    private Node rootNode;
    @Relationship(type = "hasMap", direction = INCOMING)
    private Course course;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
