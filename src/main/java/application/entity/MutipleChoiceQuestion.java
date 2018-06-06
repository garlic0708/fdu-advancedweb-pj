package application.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Properties;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class MutipleChoiceQuestion extends HomeWork{
    private String content;
    @Properties
    private Map<String, String> answers;
    private String correctAnswers;

    @Relationship(type = "resolve", direction = INCOMING)
    private Set<StudentAnswerForMutipleChoice> studentAnswerForMutipleChoices;

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

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<StudentAnswerForMutipleChoice> getStudentAnswerForMutipleChoices() {
        return studentAnswerForMutipleChoices;
    }

    public void setStudentAnswerForMutipleChoices(Set<StudentAnswerForMutipleChoice> studentAnswerForMutipleChoices) {
        this.studentAnswerForMutipleChoices = studentAnswerForMutipleChoices;
    }
}
