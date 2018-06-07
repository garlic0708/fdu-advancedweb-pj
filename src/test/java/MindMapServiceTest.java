import application.SpringBootWebApplication;
import application.entity.Course;
import application.entity.MindMap;
import application.entity.Teacher;
import application.service.CourseService;
import application.service.MindMapService;
import application.service.UserService;
import org.junit.Before;
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
public class MindMapServiceTest {
    @Autowired
    private CourseService courseService;
    @Autowired
    private MindMapService mindMapService;
    @Autowired
    private UserService userService;

    @Test
    public void testGetByCourse() {
        Course apue = courseService.getByName("apue");
        Set<MindMap> map = apue.getMaps();
        assertEquals(map.size(), 3);
    }

    @Test
    public void testGetByTeacher() {
        Teacher t1 = userService.getTeacherByName("t1");
        Set<MindMap> maps = mindMapService.getByTeacherId(t1.getId());
        assertEquals(maps.size(), 4);
    }

    @Test
    public void testDelete() {
//        courseService.deleteCourse(115);
//        MindMap map4 = mindMapService.getById(170);
//        assertEquals(map4.getId().longValue(), 170);

        mindMapService.deleteMindMap(170);
        mindMapService.deleteMindMap(118);
    }


}
