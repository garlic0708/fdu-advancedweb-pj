package application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Student student;
    @EndNode
    @JsonIgnore
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

    public ShortAnswerQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ShortAnswerQuestion question) {
        this.question = question;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
