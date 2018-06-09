package application.controller;

import application.entity.*;
import application.service.MindMapService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MindMapController {
    @Autowired
    private MindMapService mindMapService;

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/mindmap/add/{id}", method = RequestMethod.POST)
    public @ResponseBody MindMap addMindMap(
            @PathVariable String courseId,
            @RequestBody MindMap map) {
        return mindMapService.addMindMap(Long.parseLong(courseId), map.getName());
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/mindmap/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody void deleteMindMap(
            @PathVariable String id) {
        mindMapService.deleteMindMap(Long.parseLong(id));
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/mindmap/update/", method = RequestMethod.POST)
    public @ResponseBody MindMap updateMindMap(
            @RequestBody MindMap map) {
        return mindMapService.updateMindMap(map);
    }

    //get by mindmapId
    @RequestMapping(value = "/api/mindmap/get/{id}", method = RequestMethod.GET)
    public @ResponseBody MindMap get(
            @PathVariable String id,
            Principal principal) {
//        CurrentUser user = (CurrentUser) principal;
//        if (user.getRole() == Role.TEACHER) {
//            Teacher teacher = (Teacher) user.getUser();
//            Set<MindMap> maps = mindMapService.getByTeacherId(teacher.getId());
//        }
//        else {
//            Student student = (Student) user.getUser();
//        }
        return mindMapService.getById(Long.parseLong(id));
    }

    //get by root node
    // @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping(value = "/api/mindmap/getByRootNode/{id}", method = RequestMethod.GET)
    public @ResponseBody MindMap getByRootNode(
            @PathVariable String id) {
        return mindMapService.getByRootNodeId(Long.parseLong(id));
    }
}
