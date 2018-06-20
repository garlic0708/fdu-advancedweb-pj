package application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
@NodeEntity
public class Node {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private Integer internalId;
    private String color;

    @Relationship(type = "hasChild")
    private Set<Node> childNodes;

    @Relationship(type = "hasHomeWork")
    private Set<HomeWork> homeWork;

    @Relationship(type = "hasCourseWares")
    private Set<Courseware> coursewares;

    @Relationship(type = "hasResource")
    private Set<Resource> resources;

    @JsonIgnore
    @Relationship(type = "hasRootNode", direction = INCOMING)
    private MindMap fatherMap;
    @JsonIgnore
    @Relationship(type = "hasChild", direction = INCOMING)
    private Node fatherNode;

    public int getInternalId() {
        return internalId;
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Node() {
        childNodes = new HashSet<>();
        homeWork = new HashSet<>();
        coursewares = new HashSet<>();
        resources = new HashSet<>();
    }

    public Node(Integer internalId) {
        this();
        this.internalId = internalId;
    }

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

    public void addChild(Node child) {
        childNodes.add(child);
    }

    // 不确定是否能拿到对应的child
    public void removeChild(Node child) {
        childNodes.remove(findChildById(child.getId()));
        //childNodes.remove(child);
    }

    public Set<Node> getChildNodes() {
        if (childNodes == null)
            childNodes = new HashSet<>();
        return childNodes;
    }

    private Node findChildById(long childId) {
        for (Node node : childNodes) {
            if (node.getId() == childId)
                return node;
        }
        return null;
    }

    public void addQuestion(HomeWork question) {
        homeWork.add(question);
    }

    public Set<HomeWork> getHomeWork() {
        return homeWork;
    }

    public void removeQuestion(HomeWork question) {
        homeWork.remove(findQuestionById(question.getId()));
        //homeWork.remove(question);
    }

    private HomeWork findQuestionById(long questionId) {
        for (HomeWork question : homeWork) {
            if (question.getId() == questionId)
                return question;
        }
        return null;
    }

    public void addCourseware(Courseware courseware) {
        coursewares.add(courseware);
    }

    public Set<Courseware> getCoursewares() {
        return coursewares;
    }

    public void removeCourseware(Courseware courseware) {
        coursewares.remove(findCoursewareById(courseware.getId()));
        //coursewares.remove(courseware);
    }

    private Courseware findCoursewareById(long id) {
        for (Courseware courseware : coursewares) {
            if (courseware.getId() == id)
                return courseware;
        }
        return null;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }

    public Set<Resource> getResources() {
        return resources;
    }

    public void removeResource(Resource resource) {
        resources.remove(findResourceById(resource.getId()));
        //resources.remove(resource);
    }

    private Resource findResourceById(long resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId)
                return resource;
        }
        return null;
    }

    public MindMap getFatherMap() {
        return fatherMap;
    }

    public void setFatherMap(MindMap fatherMap) {
        this.fatherMap = fatherMap;
    }

    public Node getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(Node fatherNode) {
        this.fatherNode = fatherNode;
    }
}
