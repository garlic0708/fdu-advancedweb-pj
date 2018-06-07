import application.SpringBootWebApplication;
import application.entity.*;
import application.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class RepositoryTest {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private MultipleChoiceRepository multipleChoiceRepository;
    @Autowired
    private ShortAnswerRepository shortAnswerRepository;
    @Autowired
    private StudentAnswerForMultipleChoiceRepository answerForChoiceRepository;
    @Autowired
    private StudentAnswerForShortAnswerRepository answerForShortAnswerRepository;

    @Test
    @Rollback
    public void testAddAndSearchCourse() {
        Course course = new Course();
        course.setName("math");

        courseRepository.save(course);

        Course math = courseRepository.findByName("math");
        assert Objects.equals(math.getId(), course.getId());
    }

    @Test
    public void testMindMap() {
        courseRepository.deleteAll();
        mindMapRepository.deleteAll();
        Course course = new Course();
        course.setName("math");
        MindMap mindMap = new MindMap();
        mindMap.setName("function");
        course.addMap(mindMap);
        courseRepository.save(course);
        Course math = courseRepository.findByName("math");
        assertEquals(math.getMaps().size(), 1);
    }

    @Test
    public void testTeacherAndCourse() {
        Teacher teacher = new Teacher();
        teacher.setName("txh");

        Course course = courseRepository.findByName("math");

        teacher.addCourse(course);
        teacherRepository.save(teacher);
        Teacher txh = teacherRepository.findByName("txh");

        assertEquals(course.getId(), txh.getCourses().iterator().next().getId());
    }

    @Test
    public void testStudentAndCourse() {
        Student student = new Student();
        student.setName("Xu");

        Course course = courseRepository.findByName("math");
        student.addCourses(course);
        studentRepository.save(student);

        course = courseRepository.findByName("math");
        assertEquals(course.getStudents().iterator().next().getId(), student.getId());
    }

    @Test
    public void testMutiStudentsAndCourse() {
        Student jia = new Student();
        jia.setName("Jia");

        Student rui = new Student();
        rui.setName("rui");

        Course course = courseRepository.findByName("math");
        jia.addCourses(course);
        rui.addCourses(course);
        studentRepository.save(jia);
        studentRepository.save(rui);

        course = courseRepository.findByName("math");
        assertEquals(course.getStudents().size(), 3);
    }

    @Test
    public void testDeleteTeacher() {
        Teacher teacher = teacherRepository.findByName("txh");
        Course math = courseRepository.findByName("math");

        assertEquals(math.getTeacher().getId(), teacher.getId());
        teacherRepository.delete(teacher);
        assertEquals(teacherRepository.findByName("txh"), null);
        math=courseRepository.findByName("math");
        assertEquals(math.getTeacher(), null);
    }

    @Test
    public void testMapAndNodes() {
        nodeRepository.deleteAll();
        MindMap map = mindMapRepository.findByName("function");

        Node root = new Node();
        root.setName("function");

        Node trigonometricFunction = new Node();
        trigonometricFunction.setName("Trigonometric functions");
        Node sineFunction = new Node();
        sineFunction.setName("Sine function");
        Node cosineFunction = new Node();
        cosineFunction.setName("Cosine function");

        root.addChild(trigonometricFunction);
        trigonometricFunction.addChild(sineFunction);
        trigonometricFunction.addChild(cosineFunction);
        map.setRootNode(root);
        mindMapRepository.save(map);

        map = mindMapRepository.findByName("function");
        assertEquals(map.getRootNode().getName(), "function");
       // assertEquals(map.getRootNode().getChildNodes().size(), 1);

        root = nodeRepository.findByName("function");
        assertEquals(root.getChildNodes().size(), 1);

//        assertEquals(root.getChildNodes().iterator().next().getChildNodes().size(), 1);
    }

    @Test
    public void testQuestionAndNode() {
        Node sineFunction = nodeRepository.findByName("Sine function");
        MultipleChoiceQuestion mutipleChoice = new MultipleChoiceQuestion();
        mutipleChoice.setContent("sin30=?");
        mutipleChoice.addAnswer("a", "0.3");
        mutipleChoice.addAnswer("b", "0.4");
        mutipleChoice.addAnswer("c", "0.5");
        mutipleChoice.addAnswer("d", "0.6");
        mutipleChoice.addCorrectAnswer("c");

        ShortAnswerQuestion shortAnswer = new ShortAnswerQuestion();
        shortAnswer.setContent("sin90=?");
        shortAnswer.setCorrectAnswer("0");

        sineFunction.addQuestion(mutipleChoice);
        sineFunction.addQuestion(shortAnswer);
        nodeRepository.save(sineFunction);

        Node testNode = nodeRepository.findByName("Sine function");
        assertEquals(testNode.getHomeWork().size(), 2);
    }

    @Test
    public void testAnswerForQuestion() {
        Student Xu = studentRepository.findByName("Xu");
        MultipleChoiceQuestion mutipleChoice = multipleChoiceRepository.findById(163);
        ShortAnswerQuestion shortAnswerQ = shortAnswerRepository.findById(164);

        StudentAnswerForMultipleChoice choice = new StudentAnswerForMultipleChoice();
        choice.addAnswer("c");
        StudentAnswerForShortAnswer shortAnswer = new StudentAnswerForShortAnswer();
        shortAnswer.setAnswer("0");

        choice.setQuestion(mutipleChoice);
        choice.setStudent(Xu);

        shortAnswer.setQuestion(shortAnswerQ);
        shortAnswer.setStudent(Xu);

        answerForChoiceRepository.save(choice);
        answerForShortAnswerRepository.save(shortAnswer);

        Xu = studentRepository.findByName("Xu");
        assertEquals(Xu.getStudentAnswerForMultipleChoice().size(), 1);

        mutipleChoice = multipleChoiceRepository.findById(163);
        assertEquals(mutipleChoice.getStudentAnswerForMultipleChoices().size(), 1);
    }

    @Test
    public void testDeleteAnswer() {
        answerForShortAnswerRepository.deleteAll();
        answerForChoiceRepository.deleteAll();
    }
}