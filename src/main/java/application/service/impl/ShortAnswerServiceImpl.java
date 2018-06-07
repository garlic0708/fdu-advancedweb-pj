package application.service.impl;

import application.entity.Node;
import application.entity.ShortAnswerQuestion;
import application.entity.Student;
import application.entity.StudentAnswerForShortAnswer;
import application.repository.NodeRepository;
import application.repository.ShortAnswerRepository;
import application.repository.StudentAnswerForShortAnswerRepository;
import application.repository.StudentRepository;
import application.service.ShortAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/7.
 */
@Service
public class ShortAnswerServiceImpl implements ShortAnswerService {
    @Autowired
    private ShortAnswerRepository shortAnswerRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentAnswerForShortAnswerRepository answerForShortAnswerRepository;

    @Override
    public ShortAnswerQuestion getById(long id) {
        return shortAnswerRepository.findById(id);
    }

    @Override
    public Set<ShortAnswerQuestion> getByNodeId(long nodeId) {
        return shortAnswerRepository.findByFatherNode_Id(nodeId);
    }

    @Override
    public Set<ShortAnswerQuestion> getByMindMapId(long mindMapId) {
        return null;
    }

    @Override
    public void addShortAnswer(long nodeId, String content, String correctAnswer) {
        ShortAnswerQuestion question = new ShortAnswerQuestion();
        question.setContent(content);
        question.setCorrectAnswer(correctAnswer);

        Node node = nodeRepository.findById(nodeId);
        node.addQuestion(question);
        nodeRepository.save(node);
    }

    @Override
    public void addStudentAnswer(long questionId, long studentId, String answer) {
        ShortAnswerQuestion question = shortAnswerRepository.findById(questionId);
        Student student = studentRepository.findById(studentId);
        StudentAnswerForShortAnswer answerForShortAnswer = new StudentAnswerForShortAnswer();
        answerForShortAnswer.setAnswer(answer);
        answerForShortAnswer.setStudent(student);
        answerForShortAnswer.setQuestion(question);
        answerForShortAnswerRepository.save(answerForShortAnswer);
    }

    @Override
    public Set<StudentAnswerForShortAnswer> getAnswersByQuestionId(long questionId) {
        return answerForShortAnswerRepository.findByStudent_Id(questionId);
    }

    @Override
    public Set<StudentAnswerForShortAnswer> getAnswersByStudentId(long studentId) {
        return answerForShortAnswerRepository.findByStudent_Id(studentId);
    }

    @Override
    public void deleteShortAnswer(long id) {
        shortAnswerRepository.deleteById(id);
    }

    @Override
    public void update(ShortAnswerQuestion question) {
        shortAnswerRepository.save(question);
    }
}
