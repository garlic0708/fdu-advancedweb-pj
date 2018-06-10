package application.entity;

import java.util.List;

/**
 * Creator: DreamBoy
 * Date: 2018/6/9.
 */
public class AddMCQ {
    private long id;
    private String content;
    private List<String> choices;
    private int correct;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
