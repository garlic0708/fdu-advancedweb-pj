package application.controller;

import application.controller.util.CurrentUserUtil;
import application.entity.Courseware;
import application.entity.CurrentUser;
import application.entity.ResourceType;
import application.entity.Role;
import application.service.CoursewareService;
import application.service.ResourceService;
import application.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Creator: DreamBoy
 * Date: 2018/6/8.
 */
@Controller
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
    private final CoursewareService coursewareService;
    private final ResourceService resourceService;
    private final UploadService uploadService;

    @Autowired
    public FileController(CoursewareService coursewareService, ResourceService resourceService, UploadService uploadService) {
        this.coursewareService = coursewareService;
        this.resourceService = resourceService;
        this.uploadService = uploadService;
    }

    //处理文件上传
    @RequestMapping(value = "/api/upload/{type}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity jsonResult(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nodeId") long nodeId,
            @PathVariable("type") String type,
            HttpServletRequest request) {
        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        if (currentUser != null && (currentUser.getRole() == Role.TEACHER)) {
            // String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
        /*System.out.println("fileName-->" + fileName);
        System.out.println("getContentType-->" + contentType);*/
            String filePath = request.getSession().getServletContext().getRealPath(
                    String.format("/%s/", Calendar.getInstance().getTime()));
            try {
                uploadService.uploadFile(file.getBytes(), filePath, fileName);
                return ResponseEntity.ok(
                        type.equals("courseware") ?
                                coursewareService.addCourseware(nodeId, fileName, filePath) :
                                resourceService.addResource(nodeId, fileName, filePath, ResourceType.FILE));
            } catch (Exception e) {
                //json 格式可以用String返回吗？
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else
            return ResponseEntity.badRequest().build();

    }
}
