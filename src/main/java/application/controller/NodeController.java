package application.controller;

import application.entity.CurrentUser;
import application.entity.Node;
import application.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @RequestMapping(value = "/api/node/getAll/{mindMapId}", method = RequestMethod.GET)
    public @ResponseBody List<Node> getAllNodesByMindMapId(
            @PathVariable String mindMapId,
            Principal principal) {
//        CurrentUser user = ((CurrentUser) principal); //权限测试


        return nodeService.getAll(Long.parseLong(mindMapId));
//
//        if (user != null) { //权限测试不够
//            return nodeService.getAll(Long.parseLong(mindMapId));
//        }
//        else
//            return null;
    }
}
