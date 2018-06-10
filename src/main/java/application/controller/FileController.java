package application.controller;

import application.controller.result.JsonResult;
import application.entity.CurrentUser;
import application.entity.Role;
import application.entity.User;
import application.service.CoursewareService;
import application.service.UserService;
import ch.qos.logback.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

/**
 * Creator: DreamBoy
 * Date: 2018/6/8.
 */
@Controller
public class FileController {
    @Autowired
    private UserService userService;
    @Autowired
    private CoursewareService coursewareService;

    //处理文件上传
    @RequestMapping(value="/api/uploadCourseware", method = RequestMethod.POST)
    public @ResponseBody JsonResult jsonResult(
            @RequestParam("courseware") MultipartFile file,
            @RequestParam("nodeId") long nodeId,
            Principal principal,
            HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) principal;
        if (currentUser != null && (currentUser.getRole() == Role.TEACHER)) {
            // String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/
            String filePath = request.getSession().getServletContext().getRealPath("/");
            try {
                coursewareService.uploadFile(file.getBytes(), filePath, fileName);
                coursewareService.addCourseware(nodeId, fileName, filePath);
            } catch (Exception e) {
                //json 格式可以用String返回吗？
                return new JsonResult("upload courseware", "failed");
            }
            //返回json
            return new JsonResult("upload courseware", "success");
        }
        else
            return new JsonResult("upload courseware", "reject");

    }
}
