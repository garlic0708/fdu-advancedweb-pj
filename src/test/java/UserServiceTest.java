import application.SpringBootWebApplication;
import application.entity.*;
import application.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Creator: DreamBoy
 * Date: 2018/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootWebApplication.class})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testAdd() {
//        userService.addUser("t5","1", "1", Role.TEACHER);
//        userService.addUser("s6", "1", "1", Role.STUDENT);
        userService.addUser(new UserCreateForm("s6", "s6@test.com", "2", "2", Role.TEACHER));
    }

    @Test
    public void testFind() {
        Teacher teacher = (Teacher) userService.getByName("t1");
        assertEquals(teacher.getId().longValue(), 129);
    }

    @Test
    public void testGetAll() {
        Set<User> users = userService.getAll();
        assertEquals(users.size(), 3);
        userService.deleteAll();
        users = userService.getAll();
        assertEquals(users.size(), 0);
    }

    @Test
    public void testDelete() {
        Teacher teacher = (Teacher) userService.getByName("t1");
        Student s1 = (Student) userService.getByName("s1");
        Student s2 = (Student) userService.getByName("s2");

        userService.deleteUser(teacher.getId());
        userService.deleteUser(s1.getId());
        userService.deleteUser(s2.getId());

        Teacher testT1 = (Teacher) userService.getByName("t1");
        Student testS1 = (Student) userService.getByName("s1");
        Student testS2 = (Student) userService.getByName("s2");

        assertEquals(testT1, null);
        assertEquals(testS1, null);
        assertEquals(testS2, null);
    }

    @Test
    public void testUpdate() {
        testAdd();
        Student s1 = (Student) userService.getByName("s1");
        s1.setName("s3");
        userService.updateUser(s1);
        Student s3 = (Student) userService.getByName("s3");
        assertEquals(s3.getId(), s1.getId());
    }
}
