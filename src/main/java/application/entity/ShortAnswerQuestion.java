package application.entity;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */
public class ShortAnswerQuestion extends HomeWork {
    private String content;
    private String correctAnswer;

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
}
