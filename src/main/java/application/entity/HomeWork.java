package application.entity;

import application.entity.view.RoleViews;
import application.entity.view.TypeDescriptionView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
public class HomeWork implements TypeDescriptionView {
    @Id
    @GeneratedValue
    @JsonView(RoleViews.PublicView.class)
    private Long id;

    @JsonView(RoleViews.PublicView.class)
    private QuestionType type;

    @JsonIgnore
    @Relationship(type = "hasHomeWork", direction = INCOMING)
    private Node fatherNode;

    @JsonView(RoleViews.StudentView.class)
    private String answer;

    public Long getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(Node fatherNode) {
        this.fatherNode = fatherNode;
    }

    public QuestionType getQuestionType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type.toString();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
