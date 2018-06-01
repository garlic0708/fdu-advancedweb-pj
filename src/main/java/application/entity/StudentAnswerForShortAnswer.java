package application.entity;

import org.neo4j.ogm.annotation.*;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
@RelationshipEntity(type = "resolve")
public class StudentAnswerForShortAnswer {
    @Id
    @GeneratedValue
    private Long id;

    private String answer;

    @StartNode
    private Student student;
    @EndNode
    private ShortAnswerQuestion question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
