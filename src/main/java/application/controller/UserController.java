package application.controller;

import application.controller.result.JsonResult;
import application.controller.util.CurrentUserUtil;
import application.entity.ChangePasswordForm;
import application.entity.CurrentUser;
import application.entity.User;
import application.entity.UserCreateForm;
import application.service.UserService;
import application.validator.ChangePasswordValidator;
import application.validator.UserCreateFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Controller
public class UserController {
    private final UserCreateFormValidator userCreateFormValidator;
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final ChangePasswordValidator changePasswordValidator;

    @Autowired
    public UserController(UserCreateFormValidator userCreateFormValidator, UserService userService, AuthenticationManager authenticationManager, ChangePasswordValidator changePasswordValidator) {
        this.userCreateFormValidator = userCreateFormValidator;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.changePasswordValidator = changePasswordValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @InitBinder("changePassword")
    public void initBinderChangePassword(WebDataBinder binder) {
        binder.addValidators(changePasswordValidator);
    }

    @RequestMapping(value = "/afterLogin", method = RequestMethod.POST)
    public ResponseEntity afterLogin(@RequestParam(required = false) String error,
                                     @AuthenticationPrincipal CurrentUser user) {
        if (error == null)
            return ResponseEntity.ok(user.getUser());
        else
            return ResponseEntity.badRequest().build();
    }

    @RequestMapping(value = "/afterLogout", method = RequestMethod.GET)
    public ResponseEntity afterLogout() {
        return ResponseEntity.ok(new JsonResult("status", "OK"));
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

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity changePassword(
            @Valid @ModelAttribute("changePassword") ChangePasswordForm form, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getGlobalError().getDefaultMessage());
        else {
            CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            if (currentUser == null ||
                    userService.changePassword(currentUser.getUser(), form.getPassword()) == null)
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            return ResponseEntity.ok(new JsonResult("status", "ok"));
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
