package application.entity;

import application.entity.view.DescriptionView;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Courseware implements DescriptionView {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String fileLocation;

    @JsonIgnore
    @Relationship(type = "hasCourseWares", direction = INCOMING)
    private Node fatherNode;

    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Node getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(Node fatherNode) {
        this.fatherNode = fatherNode;
    }
}
