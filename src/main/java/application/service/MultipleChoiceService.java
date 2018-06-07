package application.service;
import application.entity.MultipleChoiceQuestion;
import application.entity.StudentAnswerForMultipleChoice;

import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface MultipleChoiceService {
    MultipleChoiceQuestion getById(long id);

    Set<MultipleChoiceQuestion> getByNodeId(long nodeId);

    Set<MultipleChoiceQuestion> getByMindMapId(long mindMapId);

    void addMutipleChoice(long nodeId, String content, Map<String, String> answers, String correctAnswer);

    void addStudentAnswer(long questionId, long studentId, String answer);

    Set<StudentAnswerForMultipleChoice> getAnswersByQuestionId(long questionId);

    Set<StudentAnswerForMultipleChoice> getAnswersByStudentId(long studentId);

    void deleteMutipleChoiceQuestion(long id);

    void update(MultipleChoiceQuestion question);
}
