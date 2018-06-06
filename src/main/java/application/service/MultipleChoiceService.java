package application.service;
import application.entity.MutipleChoiceQuestion;

import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
public interface MultipleChoiceService {
    MutipleChoiceQuestion getById(long id);

    Set<MutipleChoiceQuestion> getByNodeId(long nodeId);

    Set<MutipleChoiceQuestion> getByMindMapId(long mindMapId);

    void addMutipleChoice(long nodeId, String content, Map<String, String> answers, String correctAnswer);

    void deleteMutipleChoiceQuestion(long id);
}
