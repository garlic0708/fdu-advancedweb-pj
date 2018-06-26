package application.controller.event;

import application.entity.User;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;
    private Locale locale;
    private String url;

    public OnRegistrationCompleteEvent(HttpServletRequest request, User user, Locale locale) {
        super(user);

        this.user = user;
        this.locale = locale;
        url = String.format("%s://%s:%d", request.getScheme(), request.getServerName(),
                request.getServerPort());
    }

    public User getUser() {
        return user;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getUrl() {
        return url;
    }
}