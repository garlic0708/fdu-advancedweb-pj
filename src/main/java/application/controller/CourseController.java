package application.controller;

import application.controller.result.JsonResult;
import application.entity.Course;
import application.entity.CurrentUser;
import application.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Creator: DreamBoy
 * Date: 2018/6/9.
 */
@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    // @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value="/api/course/add", method = RequestMethod.POST)
    public @ResponseBody Course addCourse (
            @RequestBody Course course,
            Principal principal) {
//        return courseService.addCourse(((CurrentUser) principal).getId(), courseName);
        return courseService.addCourse(129, course.getName());
    }

    // @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value="/api/course/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteCourse (
            @PathVariable String id,
            Principal principal) {
        courseService.deleteCourse(Long.parseLong(id));
    }
}
