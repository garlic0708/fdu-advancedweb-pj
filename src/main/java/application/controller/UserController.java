package application.controller;

import application.entity.CurrentUser;
import application.entity.User;
import application.entity.UserCreateForm;
import application.service.UserService;
import application.validator.UserCreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Currency;

@Controller
public class UserController {
    private final UserCreateFormValidator userCreateFormValidator;
    private UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserCreateFormValidator userCreateFormValidator, UserService userService, AuthenticationManager authenticationManager) {
        this.userCreateFormValidator = userCreateFormValidator;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @RequestMapping(value = "/afterLogin", method = RequestMethod.POST)
    public ResponseEntity afterLogin(@RequestParam(required = false) String error,
                                     @AuthenticationPrincipal CurrentUser user) {
        if (error == null)
            return ResponseEntity.ok(user.getUser());
        else
            return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(
            HttpServletRequest request,
            @Valid @ModelAttribute("form") UserCreateForm form, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getGlobalError().getDefaultMessage());
        else {
            User user = userService.addUser(form);
            authenticateUser(request, form);
            return ResponseEntity.ok().body(user);
        }
    }

    private void authenticateUser(HttpServletRequest request, UserCreateForm form) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
