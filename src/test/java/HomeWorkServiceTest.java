import application.SpringBootWebApplication;
import application.entity.MultipleChoiceQuestion;
import application.service.HomeWorkService;
import application.service.MultipleChoiceService;
import application.service.NodeService;
import application.service.ShortAnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class HomeWorkServiceTest {
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private MultipleChoiceService multipleChoiceService;
    @Autowired
    private ShortAnswerService shortAnswerService;
    @Autowired
    private NodeService nodeService;

    private final static Logger log = LoggerFactory.getLogger(SpringBootWebApplication.class);

    @Test
    public void testAddAndGetQuestion() {
        Long nodeId = nodeService.getByName("child2_1").getId();
        Long nodeId1 = nodeService.getByName("child3_1").getId();

//        //for multiple choice
//        String content = "sin30=?";
//        Map<String, String> answers = new HashMap<>();
//        answers.put("a", "0.3");
//        answers.put("b", "0.4");
//        answers.put("c", "0.5");
//        answers.put("d", "0.6");
//        String correctAnswer = "c";
//        multipleChoiceService.addMultipleChoice(nodeId, content, answers, correctAnswer);
//
//        content = "cos60=?";
//        answers.clear();
//        answers.put("a", "0.3");
//        answers.put("b", "0.4");
//        answers.put("c", "0.5");
//        answers.put("d", "0.6");
//        correctAnswer = "c";
//        multipleChoiceService.addMultipleChoice(nodeId1, content, answers, correctAnswer);
//
//        // for short answer
//        content = "sin90=?";
//        correctAnswer = "0";
//        shortAnswerService.addShortAnswer(nodeId,content, correctAnswer);

        assertEquals(nodeService.getById(nodeId).getHomeWork().size(), 2);

        Set<MultipleChoiceQuestion> multipleChoiceQuestions = multipleChoiceService.getByNodeId(nodeId);
        log.info(multipleChoiceQuestions.toString());
        assertEquals(multipleChoiceQuestions.size(), 1);
        assertEquals(shortAnswerService.getByNodeId(nodeId).size(), 1);
        assertEquals(multipleChoiceService.getByNodeId(nodeId1).size(), 1);

        assertEquals(homeWorkService.getByNodeId(nodeId).size(), 2);
        assertEquals(homeWorkService.getByNodeId(nodeId1).size(), 1);
    }
}
