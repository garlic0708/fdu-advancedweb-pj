package application.controller;

import application.entity.Course;
import application.entity.Student;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    // @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value="/api/student/getByCourse/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Set<Student> getStudentsByCourse (
            @PathVariable String id,
            Principal principal) {
        return userService.getStudentsByCourseId(Long.parseLong(id));
    }
}
