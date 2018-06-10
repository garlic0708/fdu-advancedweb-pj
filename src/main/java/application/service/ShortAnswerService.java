package application.service;

import application.entity.ShortAnswerQuestion;
import application.entity.StudentAnswerForShortAnswer;

import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface ShortAnswerService {
    ShortAnswerQuestion getById(long id);

    Set<ShortAnswerQuestion> getByNodeId(long nodeId);

    Set<ShortAnswerQuestion> getByMindMapId(long mindMapId);

    ShortAnswerQuestion addShortAnswer(long nodeId, String content, String correctAnswer);

    StudentAnswerForShortAnswer addStudentAnswer(long questionId, long studentId, String answer);

    Set<StudentAnswerForShortAnswer> getAnswersByQuestionId(long questionId);

    Set<StudentAnswerForShortAnswer> getAnswersByStudentId(long studentId);

    void deleteShortAnswer(long id);

    ShortAnswerQuestion update(ShortAnswerQuestion question);
}
