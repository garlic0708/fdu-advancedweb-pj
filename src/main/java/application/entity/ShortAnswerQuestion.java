package application.entity;

import application.entity.view.RoleViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class ShortAnswerQuestion extends HomeWork {
    @JsonView(RoleViews.PublicView.class)
    private String content;
    @JsonView(RoleViews.TeacherView.class)
    private String correctAnswer;

    public ShortAnswerQuestion() {
        this.setType(QuestionType.SHORT_ANSWER);
    }

    @Relationship(type = "resolve", direction = INCOMING)
    @JsonIgnore
    private Set<StudentAnswerForShortAnswer> studentAnswerForShortAnswers;

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<StudentAnswerForShortAnswer> getStudentAnswerForShortAnswers() {
        return studentAnswerForShortAnswers;
    }

    public void setStudentAnswerForShortAnswers(Set<StudentAnswerForShortAnswer> studentAnswerForShortAnswers) {
        this.studentAnswerForShortAnswers = studentAnswerForShortAnswers;
    }

    @Override
    public String getDescription() {
        return content;
    }
}
