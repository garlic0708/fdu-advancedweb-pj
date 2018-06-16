package application.service;
import application.entity.AddMCQ;
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

    MultipleChoiceQuestion addMultipleChoice(long nodeId, AddMCQ mcq);

    StudentAnswerForMultipleChoice addStudentAnswer(long questionId, long studentId, String answer);

    Map<String, Long> getAnswersByQuestionId(long questionId);

    Set<StudentAnswerForMultipleChoice> getAnswersByStudentId(long studentId);

    void deleteMultipleChoiceQuestion(long id);

    void deleteAll();

    MultipleChoiceQuestion update(AddMCQ mcq);
}
