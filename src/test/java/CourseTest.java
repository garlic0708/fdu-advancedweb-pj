import application.SpringBootWebApplication;
import application.entity.Course;
import application.entity.MindMap;
import application.entity.Teacher;
import application.repository.CourseRepository;
import application.repository.MindMapRepository;
import application.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/1.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class CourseTest {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    TeacherRepository teacherRepository;

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
    public void testDeleteTeacher() {
        Teacher teacher = teacherRepository.findByName("txh");
        Course math = courseRepository.findByName("math");

        assertEquals(math.getTeacher().getId(), teacher.getId());
        teacherRepository.delete(teacher);
        assertEquals(teacherRepository.findByName("txh"), null);
        math=courseRepository.findByName("math");
        assertEquals(math.getTeacher(), null);
    }
}