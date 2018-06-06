package application.service;

import application.entity.ShortAnswerQuestion;

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

    void addShortAnswer(long nodeId, String content, String answer, String correctAnswer);

    void deleteShortAnswer(long id);
}
