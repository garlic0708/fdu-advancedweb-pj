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
    public ShortAnswerQuestion addShortAnswer(long nodeId, String content, String correctAnswer) {
        ShortAnswerQuestion question = new ShortAnswerQuestion();
        question.setContent(content);
        question.setCorrectAnswer(correctAnswer);

        Node node = nodeRepository.findById(nodeId);
        question.setFatherNode(node);
        return shortAnswerRepository.save(question);
    }

    @Override
    public StudentAnswerForShortAnswer addStudentAnswer(long questionId, long studentId, String answer) {
        ShortAnswerQuestion question = shortAnswerRepository.findById(questionId);
        Student student = studentRepository.findById(studentId);
        StudentAnswerForShortAnswer answerForShortAnswer = new StudentAnswerForShortAnswer();
        answerForShortAnswer.setAnswer(answer);
        answerForShortAnswer.setStudent(student);
        answerForShortAnswer.setQuestion(question);
        return answerForShortAnswerRepository.save(answerForShortAnswer);
    }

    @Override
    public Set<StudentAnswerForShortAnswer> getAnswersByQuestionId(long questionId) {
        return answerForShortAnswerRepository.findByQuestion_Id(questionId);
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
    public ShortAnswerQuestion update(ShortAnswerQuestion question) {
        ShortAnswerQuestion question1 = shortAnswerRepository.findById(question.getId().longValue());
        question1.setContent(question.getContent());
        question1.setCorrectAnswer(question.getCorrectAnswer());
        question1.setStudentAnswerForShortAnswers(null);
        return shortAnswerRepository.save(question1);
    }

    @Override
    public void deleteAll() {
        shortAnswerRepository.deleteAll();
    }
}
