package application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
@RelationshipEntity(type = "resolve")
public class StudentAnswerForMultipleChoice {
    @Id
    @GeneratedValue
    private Long id;
    private String answer;
    @StartNode
    @JsonIgnore
    private Student student;
    @EndNode
    @JsonIgnore
    private MultipleChoiceQuestion question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) { //a,b,c,d...
        this.answer = answer;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public MultipleChoiceQuestion getQuestion() {
        return question;
    }

    public void setQuestion(MultipleChoiceQuestion question) {
        this.question = question;
    }
}
