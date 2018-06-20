package application.service.impl;

import application.entity.*;
import application.repository.*;
import application.service.MultipleChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public MultipleChoiceQuestion getById(long id, long studentId) {
        MultipleChoiceQuestion question = getById(id);
        Set<StudentAnswerForMultipleChoice> answers = question.getStudentAnswerForMultipleChoices();
        if (answers != null)
            question.setAnswer(answers
                    .stream().filter(answer -> answer.getStudent() != null &&
                            answer.getStudent().getId() == studentId)
                    .findFirst().map(StudentAnswerForMultipleChoice::getAnswer).orElse(null));
        return question;
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
    public MultipleChoiceQuestion addMultipleChoice(long nodeId, AddMCQ mcq) {
        Map<String, String> answers = new HashMap<>();
        List<String> choices = mcq.getChoices();

        char ch = 'A';
        for (int i = 0; i < choices.size(); i++) {
            answers.put(String.valueOf((char) (ch + i)), choices.get(i));
        }

        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion();
        multipleChoiceQuestion.setContent(mcq.getContent());
        multipleChoiceQuestion.addCorrectAnswer(String.valueOf((char) (ch + mcq.getCorrect())));
        multipleChoiceQuestion.setAnswers(answers);

        Node node = nodeRepository.findById(nodeId);
        multipleChoiceQuestion.setFatherNode(node);
        return choiceRepository.save(multipleChoiceQuestion);
    }

    @Override
    public StudentAnswerForMultipleChoice addStudentAnswer(long questionId, long studentId, String answer) {
        MultipleChoiceQuestion question = choiceRepository.findById(questionId);
        Student student = studentRepository.findById(studentId);
        StudentAnswerForMultipleChoice answerForMultipleChoice =
                answerForMultipleChoiceRepository.findByStudentAndQuestion(studentId, questionId);
        if (answerForMultipleChoice != null) {
            answerForMultipleChoice.setAnswer(answer);
        } else {
            answerForMultipleChoice = new StudentAnswerForMultipleChoice();
            answerForMultipleChoice.setAnswer(answer);
            answerForMultipleChoice.setQuestion(question);
            answerForMultipleChoice.setStudent(student);
        }
        return answerForMultipleChoiceRepository.save(answerForMultipleChoice);
    }

    @Override
    public Map<String, Long> getAnswersByQuestionId(long questionId) {
        return StreamSupport.stream(
                choiceRepository.findAnswersToQuestion(questionId).spliterator(), false)
                .collect(Collectors.toMap(map -> (String) map.get("answer"),
                        map -> (Long) map.get("count")));
    }

    @Override
    public Set<StudentAnswerForMultipleChoice> getAnswersByStudentId(long studentId) {
//        return answerForMultipleChoiceRepository.findByStudentAndQuestion(studentId, );
        return null;
    }

    @Override
    public void deleteMultipleChoiceQuestion(long id) {
        nodeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        nodeRepository.deleteAll();
    }

    @Override
    public MultipleChoiceQuestion update(long id, AddMCQ mcq) {
        Map<String, String> answers = new HashMap<>();
        List<String> choices = mcq.getChoices();

        char ch = 'A';
        for (int i = 0; i < choices.size(); i++) {
            answers.put(String.valueOf((ch + i)), choices.get(i));
        }

        MultipleChoiceQuestion multipleChoiceQuestion = choiceRepository.findById(id);
        multipleChoiceQuestion.setContent(mcq.getContent());
        multipleChoiceQuestion.addCorrectAnswer(String.valueOf((ch + mcq.getCorrect())));
        multipleChoiceQuestion.setAnswers(answers);

        // 可能关系没有删除干净
        multipleChoiceQuestion.setStudentAnswerForMultipleChoices(null);

        return choiceRepository.save(multipleChoiceQuestion);
    }
}
