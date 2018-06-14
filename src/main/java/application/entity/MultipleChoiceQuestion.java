package application.entity;

import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class MultipleChoiceQuestion extends HomeWork {
    private String content;
    @Properties
    private Map<String, String> answers;
    private String correctAnswers;

    public MultipleChoiceQuestion() {
        this.setType(QuestionType.MULTIPLE_CHOICE);
    }

    @Relationship(type = "resolve", direction = INCOMING)
    private Set<StudentAnswerForMultipleChoice> studentAnswerForMultipleChoices;

    public void addAnswer(String key, String value) {
        if (answers == null)
            answers = new HashMap<>();
        answers.put(key, value);
    }

    public void addCorrectAnswer(String answer) {
        this.correctAnswers = answer;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<StudentAnswerForMultipleChoice> getStudentAnswerForMultipleChoices() {
        return studentAnswerForMultipleChoices;
    }

    public void setStudentAnswerForMultipleChoices(Set<StudentAnswerForMultipleChoice> studentAnswerForMultipleChoices) {
        this.studentAnswerForMultipleChoices = studentAnswerForMultipleChoices;
    }
}
