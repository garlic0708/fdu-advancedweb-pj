package application.entity;

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
    private Set<String> answers;
    @StartNode
    private Student student;
    @EndNode
    private MultipleChoiceQuestion question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getAnswers() {
        return answers;
    }

    public void addAnswer(String answer) { //a,b,c,d...
        if (answers == null) {
            answers = new HashSet<>();
        }
        answers.add(answer);
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
