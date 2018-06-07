package application.service;
import application.entity.MultipleChoiceQuestion;

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

    void deleteMutipleChoiceQuestion(long id);

    void update(MultipleChoiceQuestion question);
}
