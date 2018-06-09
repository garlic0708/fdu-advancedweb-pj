package application.controller;

import application.entity.*;
import application.service.HomeWorkService;
import application.service.MultipleChoiceService;
import application.service.ShortAnswerService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creator: DreamBoy
 * Date: 2018/6/9.
 */
@Controller
public class HomeWorkController {
    @Autowired
    private HomeWorkService homeWorkService;
    @Autowired
    private ShortAnswerService shortAnswerService;
    @Autowired
    private MultipleChoiceService multipleChoiceService;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/addMC/{id}", method = RequestMethod.POST)
    public @ResponseBody MultipleChoiceQuestion addMC(
            @RequestBody AddMCQ mcq,
            @PathVariable String id,
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();
        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
        if (testId == teacher.getId())
            return multipleChoiceService.addMutipleChoice(Long.parseLong(id), mcq);
        else
            return null;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/addSA/{id}", method = RequestMethod.POST)
    public @ResponseBody
    HomeWork addSA(
            @PathVariable String id,
            @RequestBody ShortAnswerQuestion saq,
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();
        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
        if (testId == teacher.getId())
            return shortAnswerService.addShortAnswer(Long.parseLong(id),saq.getContent(), saq.getCorrectAnswer());
        else
            return null;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/deleteMC/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteMC(
            @PathVariable String id,
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();
        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
        if (testId == teacher.getId())
            multipleChoiceService.deleteMutipleChoiceQuestion(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/deleteSA/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    void deleteSA(
            @PathVariable String id,
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();
        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
        if (testId == teacher.getId())
            shortAnswerService.deleteShortAnswer(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/updateMC/", method = RequestMethod.POST)
    public @ResponseBody MultipleChoiceQuestion updateMC(
            @RequestBody AddMCQ mcq,
            @PathVariable String id, //是否直接通过mcq获取
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();
        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
        if (testId == teacher.getId())
            return multipleChoiceService.update(mcq);
        else
            return null;
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/homework/updateSA/", method = RequestMethod.POST)
    public @ResponseBody ShortAnswerQuestion updateSA(
            @RequestBody ShortAnswerQuestion saq,
            @PathVariable String id, //是否直接通过mcq获取
            Principal principal) {
        CurrentUser user = (CurrentUser) principal;
        Teacher teacher = (Teacher) user.getUser();
        long testId = userService.getTeacherByNodeId(Long.parseLong(id)).getId();
        if (testId == teacher.getId())
            return shortAnswerService.update(saq);
        else
            return null;
    }

    @RequestMapping(value = "/api/homework/getMC/{id}", method = RequestMethod.GET)
    public @ResponseBody MultipleChoiceQuestion getMC(
            @PathVariable String id,
            Principal principal) {
        return multipleChoiceService.getById(Long.parseLong(id));

    }

    @RequestMapping(value = "/api/homework/updateSA/{id}", method = RequestMethod.GET)
    public @ResponseBody ShortAnswerQuestion getSA(
            @PathVariable String id,
            Principal principal) {
        return shortAnswerService.getById(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/addMCA/", method = RequestMethod.POST)
    public @ResponseBody StudentAnswerForMultipleChoice addMCA(
            @RequestBody StudentAnswer sa,
            Principal principal) { //权限添加
        return multipleChoiceService.addStudentAnswer(sa.getQuestionId(), sa.getStudentId(), sa.getAnswer());
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/addSAA/", method = RequestMethod.POST)
    public @ResponseBody StudentAnswerForShortAnswer addSAA(
            @RequestBody StudentAnswer sa,
            Principal principal) { //权限添加
        return shortAnswerService.addStudentAnswer(sa.getQuestionId(), sa.getStudentId(), sa.getAnswer());
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/getMCA/{questionId}", method = RequestMethod.GET)
    public @ResponseBody Set<StudentAnswerForMultipleChoice> getMCA(
            @PathVariable String questionId,
            Principal principal) { //权限添加
        return multipleChoiceService.getAnswersByQuestionId(Long.parseLong(questionId));
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping(value = "/api/homework/getSAA/{questionId}", method = RequestMethod.GET)
    public @ResponseBody Set<StudentAnswerForShortAnswer> getSAA(
            @PathVariable String questionId,
            Principal principal) { //权限添加
        return shortAnswerService.getAnswersByQuestionId(Long.parseLong(questionId));
    }
}
