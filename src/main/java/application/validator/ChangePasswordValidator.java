package application.validator;

import application.controller.util.CurrentUserUtil;
import application.entity.ChangePasswordForm;
import application.entity.CurrentUser;
import application.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangePasswordValidator.class);
    private final UserService userService;

    @Autowired
    public ChangePasswordValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ChangePasswordForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        ChangePasswordForm form = (ChangePasswordForm) target;

        validatePasswordCorrect(errors, form);
        validatePasswords(errors, form);
    }

    private void validatePasswords(Errors errors, ChangePasswordForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validatePasswordCorrect(Errors errors, ChangePasswordForm form) {
        CurrentUser currentUser = CurrentUserUtil.getCurrentUser();
        if (currentUser == null || !new BCryptPasswordEncoder().matches(form.getOldPassword(),
                currentUser.getUser().getPasswordHash()))
            errors.reject("password.incorrect", "Wrong password");
    }
}
