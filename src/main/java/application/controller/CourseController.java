package application.controller;

import application.controller.result.JsonResult;
import application.controller.util.CurrentUserUtil;
import application.entity.Course;
import application.entity.CurrentUser;
import application.entity.Role;
import application.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/9.
 */
@Controller
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/course/add", method = RequestMethod.POST)
    public @ResponseBody
    Course addCourse(
            @RequestBody Course course) {
//        return courseService.addCourse(((CurrentUser) principal).getId(), courseName);
        return courseService.addCourse(CurrentUserUtil.getCurrentUser().getId(),
                course.getName());
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/course/select/{id}", method = RequestMethod.POST)
    public @ResponseBody Course selectCourse(@PathVariable("id") String id) {
        return courseService.selectCourse(CurrentUserUtil.getCurrentUser().getId(),
                Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/course/deselect/{id}", method = RequestMethod.POST)
    public ResponseEntity deselectCourse(@PathVariable("id") String id) {
        courseService.deselectCourse(CurrentUserUtil.getCurrentUser().getId(),
                Long.parseLong(id));
        return ResponseEntity.ok(new JsonResult("status", "ok"));
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/course/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteCourse(
            @PathVariable String id,
            Principal principal) {
        courseService.deleteCourse(Long.parseLong(id));
    }

    @RequestMapping(value = "/api/course/get", method = RequestMethod.GET)
    public @ResponseBody
    Set<Course> getCourse() {
        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        if (currentUser.getRole() == Role.TEACHER)
            return courseService.getByTeacherId(currentUser.getId());
        else
            return courseService.getByStudentId(currentUser.getId());
    }

    // @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/course/get2/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Set<Course> getCourse2(
            @PathVariable String id,
            Principal principal) {
        return courseService.getByStudentId(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/course/query/{name}", method = RequestMethod.GET)
    public @ResponseBody
    Set<Course> queryByName(@PathVariable("name") String name) {
        return courseService.queryByName(name,
                CurrentUserUtil.getCurrentUser().getId());
    }
}
