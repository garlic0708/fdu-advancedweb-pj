package application.controller;

import application.controller.util.CurrentUserUtil;
import application.entity.*;
import application.entity.view.RoleViews;
import application.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/9.
 */
@Controller
public class AttachmentController {
    private final ShortAnswerService shortAnswerService;
    private final MultipleChoiceService multipleChoiceService;
    private final UserService userService;
    private final CoursewareService coursewareService;
    private final ResourceService resourceService;

    @Autowired
    public AttachmentController(ShortAnswerService shortAnswerService, MultipleChoiceService multipleChoiceService, UserService userService, CoursewareService coursewareService, ResourceService resourceService) {
        this.shortAnswerService = shortAnswerService;
        this.multipleChoiceService = multipleChoiceService;
        this.userService = userService;
        this.coursewareService = coursewareService;
        this.resourceService = resourceService;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/addMC/{id}", method = RequestMethod.POST)
    public @ResponseBody
    MultipleChoiceQuestion addMC(
            @RequestBody AddMCQ mcq,
            @PathVariable String id) {
        CurrentUser user = CurrentUserUtil.getCurrentUser();
        Teacher teacher = (Teacher) user.getUser();
//        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
//        if (testId == teacher.getId())
        return multipleChoiceService.addMultipleChoice(Long.parseLong(id), mcq);
//        else
//            return null;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/addSA/{id}", method = RequestMethod.POST)
    public @ResponseBody
    HomeWork addSA(
            @PathVariable String id,
            @RequestBody ShortAnswerQuestion saq) {
        CurrentUser user = CurrentUserUtil.getCurrentUser();
        Teacher teacher = (Teacher) user.getUser();
//        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
//        if (testId == teacher.getId())
        return shortAnswerService.addShortAnswer(Long.parseLong(id), saq.getContent(), saq.getCorrectAnswer());
//        else
//            return null;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/deleteMC/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteMC(
            @PathVariable String id) {
        CurrentUser user = CurrentUserUtil.getCurrentUser();
        Teacher teacher = (Teacher) user.getUser();
//        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
//        if (testId == teacher.getId())
        multipleChoiceService.deleteMultipleChoiceQuestion(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/deleteSA/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteSA(
            @PathVariable String id) {
        CurrentUser user = CurrentUserUtil.getCurrentUser();
        Teacher teacher = (Teacher) user.getUser();
//        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
//        if (testId == teacher.getId())
        shortAnswerService.deleteShortAnswer(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/updateMC/{id}", method = RequestMethod.POST)
    public @ResponseBody
    MultipleChoiceQuestion updateMC(
            @RequestBody AddMCQ mcq,
            @PathVariable String id //是否直接通过mcq获取
    ) {
        CurrentUser user = CurrentUserUtil.getCurrentUser();
        Teacher teacher = (Teacher) user.getUser();
//        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
//        if (testId == teacher.getId())
        return multipleChoiceService.update(Long.parseLong(id), mcq);
//        else
//            return null;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/updateSA/{id}", method = RequestMethod.POST)
    public @ResponseBody
    ShortAnswerQuestion updateSA(
            @RequestBody ShortAnswerQuestion saq,
            @PathVariable String id //是否直接通过mcq获取
    ) {
        CurrentUser user = CurrentUserUtil.getCurrentUser();
        Teacher teacher = (Teacher) user.getUser();
//        long testId = userService.getTeacherByNodeId(shortAnswerService.getById(Long.parseLong(id))
//                .getFatherNode().getId()).getId();
//        if (testId == teacher.getId())
        return shortAnswerService.update(Long.parseLong(id), saq);
//        else
//            return null;
    }

    @RequestMapping(value = "/api/homework/getMC/{id}", method = RequestMethod.GET)
    public @ResponseBody
    MappingJacksonValue getMC(
            @PathVariable String id) {
        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        MultipleChoiceQuestion question;
        Class view;
        if (currentUser.getRole() == Role.TEACHER) {
            question = multipleChoiceService.getById(Long.parseLong(id));
            view = RoleViews.TeacherView.class;
        } else {
            question = multipleChoiceService.getById(Long.parseLong(id), currentUser.getId());
            view = RoleViews.StudentView.class;
        }
        MappingJacksonValue value = new MappingJacksonValue(question);
        value.setSerializationView(view);
        return value;
    }

    @RequestMapping(value = "/api/homework/getSA/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ShortAnswerQuestion getSA(
            @PathVariable String id) {
        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        if (currentUser.getRole() == Role.TEACHER)
            return shortAnswerService.getById(Long.parseLong(id));
        else
            return shortAnswerService.getById(Long.parseLong(id), currentUser.getId());
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/addMCA/{qid}", method = RequestMethod.POST)
    public @ResponseBody
    StudentAnswerForMultipleChoice addMCA(
            @RequestBody StudentAnswer sa,
            @PathVariable String qid) { //权限添加
        return multipleChoiceService.addStudentAnswer(Long.parseLong(qid),
                CurrentUserUtil.getCurrentUser().getId(), sa.getAnswer());
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/addSAA/{qid}", method = RequestMethod.POST)
    public @ResponseBody
    StudentAnswerForShortAnswer addSAA(
            @RequestBody StudentAnswer sa,
            @PathVariable String qid) { //权限添加
        return shortAnswerService.addStudentAnswer(Long.parseLong(qid),
                CurrentUserUtil.getCurrentUser().getId(), sa.getAnswer());
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/getMCA/{questionId}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Long> getMCA(
            @PathVariable String questionId) { //权限添加
        return multipleChoiceService.getAnswersByQuestionId(Long.parseLong(questionId));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/getSAA/{questionId}", method = RequestMethod.GET)
    public @ResponseBody
    Set<StudentAnswerForShortAnswer> getSAA(
            @PathVariable String questionId) { //权限添加
        return shortAnswerService.getAnswersByQuestionId(Long.parseLong(questionId));
    }

    @RequestMapping(value = "/api/file/{type}/get/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<FileSystemResource> getFile(@PathVariable("id") String id,
                                               @PathVariable("type") String type) {
        String name = type.equals("courseware") ?
                coursewareService.getById(Long.parseLong(id)).getName() :
                resourceService.getById(Long.parseLong(id)).getName();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", name));
        return ResponseEntity.ok()
                .headers(headers)
                .body(new FileSystemResource(type.equals("courseware") ?
                        coursewareService.getFilePath(Long.parseLong(id)) :
                        resourceService.getFilePath(Long.parseLong(id))));

    }

    @RequestMapping(value = "/api/resource/add/{id}", method = RequestMethod.POST)
    public @ResponseBody
    Resource addResource(@PathVariable("id") String id,
                         @RequestBody Resource resource) {
        return resourceService.addResource(Long.parseLong(id),
                resource.getName(), resource.getUrl(), ResourceType.URL);
    }

    @RequestMapping(value = "/api/resource/get/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Resource getResource(@PathVariable("id") String id) {
        return resourceService.getById(Long.parseLong(id));
    }

    @RequestMapping(value = "/api/resource/update/{id}", method = RequestMethod.POST)
    public @ResponseBody
    Resource updateResource(@PathVariable("id") String id,
                            @RequestBody Resource resource) {
        return resourceService.updateResource(Long.parseLong(id), resource);
    }
}
