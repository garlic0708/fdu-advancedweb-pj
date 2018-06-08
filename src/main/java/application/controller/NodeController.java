package application.controller;

import application.entity.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class NodeController {

    // todo example of getting user
    @RequestMapping(value = "/...")
    public String test(Principal principal) {
        long id = ((CurrentUser) principal).getId();
        return null;
    }
}
