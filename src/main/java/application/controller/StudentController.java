package application.controller;

import application.entity.Course;
import application.entity.CurrentUser;
import application.entity.Student;
import application.entity.Teacher;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/9.
 */
@Controller
public class StudentController {
    @Autowired
    private UserService userService;
    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value="/api/student/getByCourse/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Set<Student> getStudentsByCourse (
            @PathVariable String id,
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();

        Set<Course> courseSet = teacher.getCourses();
        if (courseSet.stream().anyMatch(course -> course.getId() == Long.parseLong(id)))
            return userService.getStudentsByCourseId(Long.parseLong(id));
        return null;
    }
}
