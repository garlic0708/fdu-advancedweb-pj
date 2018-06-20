package application.service.impl;

import application.entity.CurrentUser;
import application.entity.User;
import application.service.UserService;
import application.validator.ChangePasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * This service is used by Spring Security framework, for authentication
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        CurrentUser currentUser = new CurrentUser(user);
        LOGGER.info(currentUser.toString());
        return currentUser;
    }
}
