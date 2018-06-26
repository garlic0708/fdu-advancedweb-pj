package application.controller;

import application.controller.event.OnRegistrationCompleteEvent;
import application.controller.result.JsonResult;
import application.controller.util.CurrentUserUtil;
import application.entity.*;
import application.service.UserService;
import application.validator.ChangePasswordValidator;
import application.validator.UserCreateFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import java.time.Instant;
import java.util.Date;

@Controller
public class UserController {
    private final UserCreateFormValidator userCreateFormValidator;
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final ChangePasswordValidator changePasswordValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(UserCreateFormValidator userCreateFormValidator, UserService userService, AuthenticationManager authenticationManager, ChangePasswordValidator changePasswordValidator, ApplicationEventPublisher eventPublisher) {
        this.userCreateFormValidator = userCreateFormValidator;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.changePasswordValidator = changePasswordValidator;
        this.eventPublisher = eventPublisher;
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
        LOGGER.info(form.toString());
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getGlobalError().getDefaultMessage());
        else {
            LOGGER.info("start authenticating");
            User user = userService.addUser(form);
            LOGGER.info(user.toString());
//            authenticateUser(request, form);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (request, user, request.getLocale()));
            LOGGER.info("complete publish event");
            ResponseEntity body = ResponseEntity.ok().body(new JsonResult("status", "ok"));
            LOGGER.info(body.toString());
            return body;
        }
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public ResponseEntity registrationConfirm(@RequestParam("token") String token,
                                              HttpServletRequest request) {
        VerificationToken verificationToken = userService.getToken(token);
        if (verificationToken == null)
            return ResponseEntity.badRequest().body("Invalid token");
        else if (verificationToken.getExpiryDate().before(Date.from(Instant.now())))
            return ResponseEntity.badRequest().body("Token has expired");
        else {
            User user = verificationToken.getUser();
            user = userService.activateUser(user);
//            authenticateUser(request, user);
            return ResponseEntity.ok(new JsonResult("status", "ok"));
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

//    private void authenticateUser(HttpServletRequest request, User user) {
//        CurrentUser currentUser = new CurrentUser(user);
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(currentUser, null,
//                        currentUser.getAuthorities());
//        authToken.setDetails(new WebAuthenticationDetails(request));
//        Authentication authentication = authenticationManager.authenticate(authToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }
}
