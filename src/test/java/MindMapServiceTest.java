import application.SpringBootWebApplication;
import application.entity.Course;
import application.entity.MindMap;
import application.service.CourseService;
import application.service.MindMapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void testGetByCourse() {
        courseService.addCourse(129, "testMap");
        Course course = courseService.getByName("testMap");
        course.setName("testMap");
        MindMap map4 = new MindMap();
        map4.setName("map4");
        MindMap map5 = new MindMap();
        map5.setName("map5");
        course.addMap(map4);
        course.addMap(map5);
        courseService.updateCourse(course);
    }

    @Test
    public void testDelete() {
        courseService.deleteCourse(115);
        MindMap map4 = mindMapService.getById(170);
        assertEquals(map4.getId().longValue(), 170);

//        mindMapService.deleteMindMap(166);
//        mindMapService.deleteMindMap(149);
    }
}
