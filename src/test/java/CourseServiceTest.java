import application.SpringBootWebApplication;
import application.entity.Course;
import application.entity.MindMap;
import application.entity.Student;
import application.entity.Teacher;
import application.repository.*;
import application.service.CourseService;
import application.service.UserService;
import application.service.impl.CourseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private MindMapRepository mindMapRepository;

    @Test
    public void init() {
        testAdd();;
        testSelect();
    }

    @Test
    public void testAdd() {
        courseService.deleteAll();
        Teacher t1 =userService.getTeacherByName("t1");
        //       Teacher t1 = teacherRepository.findByName("t1");
        courseService.addCourse(t1.getId(), "ooad");
        courseService.addCourse(t1.getId(), "apue");

        t1 = userService.getTeacherByName("t1");; //这一步是关键，只有老师和课程有关系，user没有
        // t1 = userService.getByName("t1");
        assertEquals(t1.getCourses().size(), 2);
        Teacher t2 = userService.getTeacherByName("t1");
        assertEquals(t2.getId(), t1.getId());
    }

    @Test
    public void testSelect() {
        Student s1 = userService.getStudentByName("s1");
        Student s2 = userService.getStudentByName("s2");

        Course ooad = courseService.getByName("ooad");
        Course apue = courseService.getByName("apue");

        courseService.selectCourse(s1.getId(), ooad.getId());
        courseService.selectCourse(s2.getId(), ooad.getId());
        courseService.selectCourse(s1.getId(), apue.getId());
        courseService.selectCourse(s2.getId(), apue.getId());

        assertEquals(courseService.getByStudentId(s1.getId()).size(), 2);
        assertEquals(userService.getStudentsByCourseId(apue.getId()).size(), 2);
    }

    @Test
    public void testDelete() {
        courseService.deleteCourse(172);
        Course apue = courseService.getById(172);
        assertEquals(apue, null);
    }

    @Test
    public void testGetByMapId() {
        mindMapRepository.deleteAll();
        MindMap map1 = new MindMap();
        map1.setName("map1");
        MindMap map2 = new MindMap();
        map2.setName("map2");
        MindMap map3 = new MindMap();
        map3.setName("map3");
        Course apue = courseService.getByName("apue");
        apue.addMap(map1);
        apue.addMap(map2);
        apue.addMap(map3);
        courseService.updateCourse(apue);
        apue = courseService.getByName("apue");
        assertEquals(apue.getMaps().size(), 3);
        MindMap mindMap = mindMapRepository.findByName("map1");
        Course course = courseService.getByMindMapId(mindMap.getId());
        assertEquals(course.getId(), apue.getId());
    }

    @Test
    public void testGetByMapName() {
        Course apue = courseService.getByName("apue");
        MindMap mindMap = mindMapRepository.findByName("map1");
        Course course = courseService.getByMindMapId(mindMap.getId());
        assertEquals(apue.getId(), course.getId());
    }

    @Test
    public void testAdd2() {
        Course course = courseService.addCourse(129, "ics");
        Course test = courseService.getByName("ics");
        assertEquals(course.getId(), test.getId());
    }

    @Test
    public void testGetWithDepth() {
        Set<Course> courseSet = courseService.getByTeacherId(129);
        Course course = courseSet.iterator().next();
        assertEquals(course.getId().longValue(), 122);
    }
}
