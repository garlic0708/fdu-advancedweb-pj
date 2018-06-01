package application.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Properties;

import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class MutipleChoiceQuestion extends HomeWork{
    private String content;
    @Properties
    private Map<String, String> answers;
    private Set<String> correctAnswers;

    public void addAnswer(String key, String value) {
        answers.put(key, value);
    }

    public void addCorrectAnswer(String answer) {
        correctAnswers.add(answer);
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public Set<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
