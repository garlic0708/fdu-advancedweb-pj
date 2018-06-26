package application.component;

import application.controller.UserController;
import application.controller.event.OnRegistrationCompleteEvent;
import application.entity.User;
import application.entity.VerificationToken;
import application.repository.VerificationTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);

//    @Autowired
//    private IUserService service;

    @Value("${spring.mail.username}")
    private String username;

    private final MessageSource messages;

    private final JavaMailSender mailSender;
    private final VerificationTokenRepository tokenRepository;

    @Autowired
    public RegistrationListener(JavaMailSender mailSender, VerificationTokenRepository tokenRepository,
                                @Qualifier("messageSource") MessageSource messages) {
        this.mailSender = mailSender;
        this.tokenRepository = tokenRepository;
        this.messages = messages;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new VerificationToken(token, user));

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = String.format("/app/confirmRegister?token=%s", token);
        String message = messages.getMessage("register.email", new Object[]{null},
                event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(username);
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(String.format("%s\n%s%s", message, event.getUrl(), confirmationUrl));
        LOGGER.info(email.toString());
        mailSender.send(email);
    }
}
