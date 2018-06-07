package application.service.impl;

import application.entity.MultipleChoiceQuestion;
import application.entity.Node;
import application.entity.Student;
import application.entity.StudentAnswerForMultipleChoice;
import application.repository.*;
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
    private final MultipleChoiceRepository choiceRepository;
    private final NodeRepository nodeRepository;
    private final StudentRepository studentRepository;
    private final StudentAnswerForMultipleChoiceRepository answerForMultipleChoiceRepository;

    @Autowired
    public MultipleChoiceServiceImpl(MultipleChoiceRepository choiceRepository, NodeRepository nodeRepository, StudentRepository studentRepository, StudentAnswerForMultipleChoiceRepository answerForMultipleChoiceRepository) {
        this.choiceRepository = choiceRepository;
        this.nodeRepository = nodeRepository;
        this.studentRepository = studentRepository;
        this.answerForMultipleChoiceRepository = answerForMultipleChoiceRepository;
    }

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
    public void addStudentAnswer(long questionId, long studentId, String answer) {
        MultipleChoiceQuestion question = choiceRepository.findById(questionId);
        Student student = studentRepository.findById(studentId);
        StudentAnswerForMultipleChoice answerForMultipleChoice = new StudentAnswerForMultipleChoice();
        answerForMultipleChoice.addAnswer(answer);
        answerForMultipleChoice.setQuestion(question);
        answerForMultipleChoice.setStudent(student);
        answerForMultipleChoiceRepository.save(answerForMultipleChoice);
    }

    @Override
    public Set<StudentAnswerForMultipleChoice> getAnswersByQuestionId(long questionId) {
        return answerForMultipleChoiceRepository.findByQuestion_Id(questionId);
    }

    @Override
    public Set<StudentAnswerForMultipleChoice> getAnswersByStudentId(long studentId) {
        return answerForMultipleChoiceRepository.findByStudent_Id(studentId);
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
