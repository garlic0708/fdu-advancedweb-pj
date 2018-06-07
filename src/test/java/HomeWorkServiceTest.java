import application.SpringBootWebApplication;
import application.service.HomeWorkService;
import application.service.MultipleChoiceService;
import application.service.NodeService;
import application.service.ShortAnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testAddAndGetQuestion() {
        //for multiple choice
//        String content = "sin30=?";
//        Map<String, String> answers = new HashMap<>();
//        answers.put("a", "0.3");
//        answers.put("b", "0.4");
//        answers.put("c", "0.5");
//        answers.put("d", "0.6");
//        String correctAnswer = "c";
//        multipleChoiceService.addMutipleChoice(156, content, answers, correctAnswer);
//
//        content = "cos60=?";
//        answers.clear();
//        answers.put("a", "0.3");
//        answers.put("b", "0.4");
//        answers.put("c", "0.5");
//        answers.put("d", "0.6");
//        correctAnswer = "c";
//        multipleChoiceService.addMutipleChoice(133, content, answers, correctAnswer);

        // for short answer
//        content = "sin90=?";
//        correctAnswer = "0";
//        shortAnswerService.addShortAnswer(156,content, correctAnswer);

        assertEquals(nodeService.getById(156).getHomeWork().size(), 2);

        assertEquals(multipleChoiceService.getByNodeId(156).size(), 1);
        assertEquals(shortAnswerService.getByNodeId(156).size(), 1);
        assertEquals(multipleChoiceService.getByNodeId(133).size(), 1);

        assertEquals(homeWorkService.getByNodeId(156).size(), 2);
        assertEquals(homeWorkService.getByNodeId(133).size(), 1);
    }
}
