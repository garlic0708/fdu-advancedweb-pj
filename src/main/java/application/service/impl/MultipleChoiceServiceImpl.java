package application.service.impl;

import application.entity.MultipleChoiceQuestion;
import application.entity.Node;
import application.repository.MultipleChoiceRepository;
import application.repository.NodeRepository;
import application.service.MultipleChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@Service
public class MultipleChoiceServiceImpl implements MultipleChoiceService {
    @Autowired
    private MultipleChoiceRepository choiceRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Override
    public MultipleChoiceQuestion getById(long id) {
        return choiceRepository.findById(id);
    }

    @Override
    public Set<MultipleChoiceQuestion> getByNodeId(long nodeId) {
        return choiceRepository.findByFatherNode_Id(nodeId);
    }

    @Override
    public Set<MultipleChoiceQuestion> getByMindMapId(long mindMapId) {
        return null;
    }

    @Override
    public void addMutipleChoice(long nodeId, String content, Map<String, String> answers, String correctAnswer) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setContent(content);
        multipleChoiceQuestion.addCorrectAnswer(correctAnswer);
        multipleChoiceQuestion.setAnswers(answers);

        Node node = nodeRepository.findById(nodeId);
        node.addQuestion(multipleChoiceQuestion);
        nodeRepository.save(node);

    }

    @Override
    public void deleteMutipleChoiceQuestion(long id) {
        nodeRepository.deleteById(id);
    }

    @Override
    public void update(MultipleChoiceQuestion question) {
        choiceRepository.save(question);
    }
}
