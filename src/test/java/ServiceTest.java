import application.SpringBootWebApplication;
import application.entity.*;
import application.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class ServiceTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CoursewareService coursewareService;
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private MindMapService mindMapService;
    @Autowired
    private MultipleChoiceService multipleChoiceService;
    @Autowired
    private ShortAnswerService shortAnswerService;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @Test
    public void testAllService() {
        deleteAll();
        testAddUser();
        testGetUser();
        testDeleteUser();
        testAddCourse();
        testGetCourse();
        testDeleteCourse();
        testAddMap();
        testGetMap();
        testDeleteMap();
        testAddNode();
        testGetNodes();
        testDeleteNode();
        testAddResource();
        testGetResource(); //getByNode 需要改善
        testDeleteResource();
        testAddCourseware();
        testGetCourseware(); //getByNode 需要改善
        testDeleteCourseware();
        testAddHomework();
    }

    //每次测试前将数据库清空
    private void deleteAll() {
        courseService.deleteAll();
        coursewareService.deleteAll();
        homeWorkService.deleteAll();
        mindMapService.deleteAll();
        multipleChoiceService.deleteAll();
        nodeService.deleteAll();
        resourceService.deleteAll();
        userService.deleteAll();
    }

    // 添加用户
    private void testAddUser() {
        UserCreateForm form = new UserCreateForm("teacher1", "teacher1@test.com","123", "123", Role.TEACHER);
        UserCreateForm form2 = new UserCreateForm("teacher2", "teacher2@test.com","123", "123", Role.TEACHER);
        UserCreateForm form3 = new UserCreateForm("student1", "student1@test.com","123", "123", Role.STUDENT);
        UserCreateForm form4 = new UserCreateForm("student2", "student2@test.com","123", "123", Role.STUDENT);
        UserCreateForm form5 = new UserCreateForm("student3", "student3@test.com","123", "123", Role.STUDENT);
        UserCreateForm form6 = new UserCreateForm("student4", "student4@test.com","123", "123", Role.STUDENT);
        UserCreateForm form7 = new UserCreateForm("student5", "student5@test.com","123", "123", Role.STUDENT);

        Teacher teacher1 = (Teacher) userService.addUser(form);
        userService.addUser(form2);
        userService.addUser(form3);
        userService.addUser(form4);
        userService.addUser(form5);
        userService.addUser(form6);
        userService.addUser(form7);

        assertEquals(teacher1.getName(), "teacher1");
    }

    // 查找用户
    private void testGetUser() {
        //getByName
        Teacher teacher1 = userService.getTeacherByName("teacher1");
        Student student2 = userService.getStudentByName("student2");
        assertEquals(teacher1.getName(), "teacher1");
        assertEquals(student2.getName(), "student2");

        teacher1 = (Teacher) userService.getByName("teacher1");
        student2 = (Student) userService.getByName("student2");
        assertEquals(teacher1.getName(), "teacher1");
        assertEquals(student2.getName(), "student2");


        //getById
        Teacher teacher3 = userService.getTeacherById(teacher1.getId());
        Student student5 = userService.getStudentById(student2.getId());
        assertEquals(teacher3.getName(), "teacher1");
        assertEquals(student5.getName(), "student2");

        teacher3 = (Teacher) userService.getById(teacher1.getId());
        student5 = (Student) userService.getById(student2.getId());
        assertEquals(teacher3.getName(), "teacher1");
        assertEquals(student5.getName(), "student2");

        //getByEmail
        Optional<User> teacher2 = userService.getByEmail("teacher2@test.com");
        Optional<User> student1 = userService.getByEmail("student1@test.com");
        assertEquals(teacher2.orElse(null).getName(), "teacher2");
        assertEquals(student1.orElse(null).getName(), "student1");

        //getAll
        Set<User> users = userService.getAll();
        assertEquals(users.size(), 7);
    }

    // 删除用户
    private void testDeleteUser() {
        Student student5 = userService.getStudentByName("student5");
        userService.deleteUser(student5.getId());
        student5 = userService.getStudentById(student5.getId());
        assertEquals(student5, null);
    }

    // 添加课程, 选课
    private void testAddCourse() {
        Teacher teacher1 = userService.getTeacherByName("teacher1");
        Teacher teacher2 = userService.getTeacherByName("teacher2");
        Student student1 = userService.getStudentByName("student1");
        Student student2 = userService.getStudentByName("student2");
        Student student3 = userService.getStudentByName("student3");
        Student student4 = userService.getStudentByName("student4");

        //add course
        Course course1 = courseService.addCourse(teacher1.getId(), "course1");
        Course course2 = courseService.addCourse(teacher1.getId(), "course2");
        Course course3 = courseService.addCourse(teacher2.getId(), "course3");
        Course course4 = courseService.addCourse(teacher2.getId(), "course4");

        //select course
        courseService.selectCourse(student1.getId(), course1.getId());
        courseService.selectCourse(student1.getId(), course2.getId());
        courseService.selectCourse(student1.getId(), course3.getId());
        courseService.selectCourse(student1.getId(), course4.getId());
        courseService.selectCourse(student2.getId(), course1.getId());
        courseService.selectCourse(student2.getId(), course2.getId());
        courseService.selectCourse(student2.getId(), course3.getId());
        courseService.selectCourse(student2.getId(), course4.getId());
        courseService.selectCourse(student3.getId(), course1.getId());
        courseService.selectCourse(student4.getId(), course1.getId());

        assertEquals(course1.getName(), "course1");
        assertEquals(course3.getTeacher().getId(), teacher2.getId());


        Teacher teacher3 = userService.getTeacherByCourseId(course1.getId());
        assertEquals(teacher1.getId(), teacher3.getId());

        Set<Student> studentSet1 = userService.getStudentsByCourseId(course1.getId());
        Set<Student> studentSet2 = userService.getStudentsByCourseId(course2.getId());
        assertEquals(studentSet1.size(), 4);
        assertEquals(studentSet2.size(), 2);
    }

    // 查找课程
    private void testGetCourse() {
        Teacher teacher1 = userService.getTeacherByName("teacher1");
        Student student1 = userService.getStudentByName("student1");


        //getByName
        Course course1 = courseService.getByName("course1");
        assertEquals(course1.getName(), "course1");

        //getById
        Course course1_1 = courseService.getById(course1.getId());
        assertEquals(course1.getId(), course1_1.getId());

        //getByTeacher
        Set<Course>  courseSet1 = courseService.getByTeacherId(teacher1.getId());
        assertEquals(courseSet1.size(), 2);

        //getByStudent
        Set<Course> courseSet2 = courseService.getByStudentId(student1.getId());
        assertEquals(courseSet2.size(), 4);
    }

    // 删除课程
    private void testDeleteCourse() {
        Course course4 = courseService.getByName("course4");
        courseService.deleteCourse(course4.getId());
        course4 = courseService.getByName("course4");
        assertEquals(course4, null);
    }

    // 添加图
    private void testAddMap() {
        Course course1 = courseService.getByName("course1");
        Course course2 = courseService.getByName("course2");
        Course course3 = courseService.getByName("course3");

        //addMap
        MindMap map1 = mindMapService.addMindMap(course1.getId(), "map1");
        MindMap map2 = mindMapService.addMindMap(course1.getId(), "map2");
        MindMap map3 = mindMapService.addMindMap(course2.getId(), "map3");
        MindMap map4 = mindMapService.addMindMap(course3.getId(), "map4");
        MindMap map5 = mindMapService.addMindMap(course3.getId(), "map5");

        course1 = courseService.getByMindMapId(map1.getId());
        assertEquals(course1.getName(), "course1");
        course2 = courseService.getByMindMapId(map1.getId());
        assertEquals(course1.getId(), course2.getId());

    }

    // 查找图
    private void testGetMap() {
        //getByName
        MindMap map1 = mindMapService.getByName("map1");
        assertEquals(map1.getName(), "map1");

        //getById
        MindMap map1_1 = mindMapService.getById(map1.getId());
        assertEquals(map1_1.getName(), "map1");

        //getBycourse
        Course course1 = courseService.getByName("course1");
        Course course2 = courseService.getByName("course2");
        Set<MindMap> mapSet1 = mindMapService.getByCourseId(course1.getId());
        assertEquals(mapSet1.size(),2);
        Set<MindMap> mapSet2 = mindMapService.getByCourseId(course2.getId());
        assertEquals(mapSet2.size(),1);

        //getByTeacher
        Teacher teacher1 = userService.getTeacherByName("teacher1");
        Teacher teacher2 = userService.getTeacherByName("teacher2");
        mapSet1 = mindMapService.getByTeacherId(teacher1.getId());
        assertEquals(mapSet1.size(), 3);
        mapSet2 = mindMapService.getByTeacherId(teacher2.getId());
        assertEquals(mapSet2.size(), 2);
    }

    //删除图
    private void testDeleteMap() {
        MindMap map4 = mindMapService.getByName("map4");
        assertEquals(map4.getName(), "map4");
        mindMapService.deleteMindMap(map4.getId());
        map4 = mindMapService.getByName("map4");
        assertEquals(map4, null);
    }

    //添加节点
    private void testAddNode() {
        MindMap map1 = mindMapService.getByName("map1");
        MindMap map2 = mindMapService.getByName("map2");
        MindMap map3 = mindMapService.getByName("map3");

        //addRoot
        Node root1 = nodeService.addRootNode(map1.getId(), "root1");
        Node root2 = nodeService.addRootNode(map2.getId(), "root2");
        Node root3 = nodeService.addRootNode(map3.getId(), "root3");

        //addNode
        Node node1 = nodeService.addNode(root1.getId(), "node1");
        Node node2 = nodeService.addNode(root1.getId(), "node2");
        Node node3 = nodeService.addNode(root2.getId(), "node3");
        Node node4 = nodeService.addNode(root2.getId(), "node4");
        Node node5 = nodeService.addNode(root3.getId(), "node5");

        Node node6 = nodeService.addNode(node1.getId(), "node6");
        Node node7 = nodeService.addNode(node1.getId(), "node7");

        Node node8 = nodeService.addNode(node6.getId(), "node8");

        Node node9 = nodeService.addNode(node8.getId(), "node9");
    }

    //查找节点，附带通过节点找Teacher
    private void testGetNodes() {
        //getByName
        Node node1 = nodeService.getByName("node1");
        assertEquals(node1.getName(), "node1");

        //getById
        Node node1_1 = nodeService.getById(node1.getId());
        assertEquals(node1_1.getName(), "node1");

        //getByFather
        Set<Node> nodes = nodeService.getByFatherNodeId(node1.getId());
        assertEquals(nodes.size(), 2);

        //getByChild
        Node child = nodes.iterator().next();
        Node node1_2 = nodeService.getByChildNodeId(child.getId());
        assertEquals(node1.getName(), node1_2.getName());

        //getRootByMindMap
        MindMap map1 = mindMapService.getByName("map1");
        Node root1 = nodeService.getRootNodeByMindMapId(map1.getId());
        assertEquals(root1.getName(), "root1");

        //getAll
        MindMap map2 = mindMapService.getByName("map2");
        Node root2 = nodeService.getAll(map2.getId());
        assertEquals(root2.getChildNodes().size(), 2);

        //getTeacher
        Teacher teacher1 = userService.getTeacherByNodeId(node1.getId());
        assertEquals(teacher1.getName(), "teacher1");
    }

    // 删除节点
    private void testDeleteNode() {
        Node node9 = nodeService.getByName("node9");
        nodeService.deleteNode(node9.getId());
        node9 = nodeService.getByName("node9");
        assertEquals(node9, null);
    }

    // 添加资源
    private void testAddResource() {
        Node node6 = nodeService.getByName("node6");
        Node node7 = nodeService.getByName("node7");
        Node node8 = nodeService.getByName("node8");

        resourceService.addResource(node6.getId(), "resource1", "./", ResourceType.FILE);
        resourceService.addResource(node6.getId(), "resource2", "./", ResourceType.URL);
        resourceService.addResource(node7.getId(), "resource3", "./", ResourceType.FILE);
        resourceService.addResource(node8.getId(), "resource4", "./", ResourceType.FILE);
    }

    // 查找资源
    private void testGetResource() {
        //getByName
        Resource resource1 = resourceService.getByName("resource1");
        assertEquals(resource1.getName(), "resource1");

        //getById
        Resource resource1_1 = resourceService.getById(resource1.getId());
        assertEquals(resource1_1.getName(), "resource1");

        //getByNode
        Node node6 = nodeService.getByName("node6");
        Set<Resource> resources = resourceService.getByNodeId(node6.getId());
        assertEquals(resources.size(), 2);
    }

    // 删除资源
    private void testDeleteResource() {
        Resource resource4 = resourceService.getByName("resource4");
        resourceService.deleteResource(resource4.getId());
        resource4 = resourceService.getByName("resource4");
        assertEquals(resource4, null);
    }

    //添加课件
    private void testAddCourseware() {
        Node node6 = nodeService.getByName("node6");
        Node node7 = nodeService.getByName("node7");
        Node node8 = nodeService.getByName("node8");

        coursewareService.addCourseware(node6.getId(), "courseware1", "./");
        coursewareService.addCourseware(node6.getId(), "courseware2", "./");
        coursewareService.addCourseware(node7.getId(), "courseware3", "./");
        coursewareService.addCourseware(node8.getId(), "courseware4", "./");
    }

    // 查找课件
    private void testGetCourseware() {
        //getByName
        Courseware courseware1 = coursewareService.getByName("courseware1");
        assertEquals(courseware1.getName(), "courseware1");

        //getById
        Courseware courseware1_1 = coursewareService.getById(courseware1.getId());
        assertEquals(courseware1_1.getName(), "courseware1");

        //getByNode
        Node node6 = nodeService.getByName("node6");
        Set<Courseware> coursewareSet = coursewareService.getByNodeId(node6.getId());
        assertEquals(coursewareSet.size(), 2);
    }

    //删除课件
    private void testDeleteCourseware() {
        Courseware courseware4 = coursewareService.getByName("courseware4");
        coursewareService.deleteCourseware(courseware4.getId());
        courseware4 = coursewareService.getByName("courseware4");
        assertEquals(courseware4, null);
    }

    // 添加作业和答案， 以及查找
    private void testAddHomework() {
        Node node6 = nodeService.getByName("node6");
        Node node7 = nodeService.getByName("node7");
        Student student1 = userService.getStudentByName("student1");
        Student student2 = userService.getStudentByName("student2");

        //for multiple choice
        String content = "sin30=?";
        List<String> choices = new ArrayList<>();
        choices.add("0.3");
        choices.add("0.4");
        choices.add("0.5");
        choices.add("0.6");
        AddMCQ addMCQ = new AddMCQ();
        addMCQ.setChoices(choices);
        addMCQ.setContent(content);
        addMCQ.setCorrect(3);
        MultipleChoiceQuestion mcq1 = multipleChoiceService.addMultipleChoice(node6.getId(), addMCQ);

        content = "cos60=?";
        choices = new ArrayList<>();
        choices.add("0.3");
        choices.add("0.4");
        choices.add("0.5");
        choices.add("0.6");
        addMCQ = new AddMCQ();
        addMCQ.setChoices(choices);
        addMCQ.setContent(content);
        addMCQ.setCorrect(3);
        MultipleChoiceQuestion mcq2 = multipleChoiceService.addMultipleChoice(node7.getId(), addMCQ);

        // for short answer
        content = "sin90=?";
        String correctAnswer = "0";
        ShortAnswerQuestion saq1 = shortAnswerService.addShortAnswer(node6.getId(),content, correctAnswer);

        node6 = nodeService.getById(node6.getId());
        assertEquals(node6.getHomeWork().size(), 2);

        //getByNodeId
        Set<MultipleChoiceQuestion> mcqs = multipleChoiceService.getByNodeId(node6.getId());
        assertEquals(mcqs.size(), 1);


        //addAnswer
        StudentAnswerForMultipleChoice sa1 = multipleChoiceService.addStudentAnswer(mcq1.getId(), student1.getId(), "C");
        StudentAnswerForMultipleChoice sa2 = multipleChoiceService.addStudentAnswer(mcq2.getId(), student1.getId(), "C");
        StudentAnswerForShortAnswer sa5 = shortAnswerService.addStudentAnswer(saq1.getId(), student1.getId(), ".......");
        StudentAnswerForMultipleChoice sa3 = multipleChoiceService.addStudentAnswer(mcq1.getId(), student2.getId(), "A");
        StudentAnswerForMultipleChoice sa4 = multipleChoiceService.addStudentAnswer(mcq2.getId(), student2.getId(), "B");
        StudentAnswerForShortAnswer sa6 = shortAnswerService.addStudentAnswer(saq1.getId(), student2.getId(), "0");

    }
}
