package application.service.impl;

import application.entity.*;
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
    private final ShortAnswerRepository shortAnswerRepository;
    private final NodeRepository nodeRepository;
    private final StudentRepository studentRepository;
    private final StudentAnswerForShortAnswerRepository answerForShortAnswerRepository;

    @Autowired
    public ShortAnswerServiceImpl(ShortAnswerRepository shortAnswerRepository, NodeRepository nodeRepository, StudentRepository studentRepository, StudentAnswerForShortAnswerRepository answerForShortAnswerRepository) {
        this.shortAnswerRepository = shortAnswerRepository;
        this.nodeRepository = nodeRepository;
        this.studentRepository = studentRepository;
        this.answerForShortAnswerRepository = answerForShortAnswerRepository;
    }

    @Override
    public ShortAnswerQuestion getById(long id) {
        return shortAnswerRepository.findById(id);
    }

    @Override
    public ShortAnswerQuestion getById(long id, long studentId) {
        ShortAnswerQuestion question = getById(id);
        Set<StudentAnswerForShortAnswer> answers = question.getStudentAnswerForShortAnswers();
        if (answers != null)
            question.setAnswer(answers
                    .stream().filter(answer -> answer.getStudent() != null &&
                            answer.getStudent().getId() == studentId)
                    .findFirst().map(StudentAnswerForShortAnswer::getAnswer).orElse(null));
        return question;
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
        StudentAnswerForShortAnswer answerForShortAnswer =
                answerForShortAnswerRepository.findByStudentAndQuestion(studentId, questionId);
        if (answerForShortAnswer != null)
            answerForShortAnswer.setAnswer(answer);
        else {
            answerForShortAnswer = new StudentAnswerForShortAnswer();
            answerForShortAnswer.setAnswer(answer);
            answerForShortAnswer.setStudent(student);
            answerForShortAnswer.setQuestion(question);
        }
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
    public ShortAnswerQuestion update(long id, ShortAnswerQuestion question) {
        ShortAnswerQuestion question1 = shortAnswerRepository.findById(id);
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
