package application.entity;

import javax.validation.constraints.NotEmpty;

public class ChangePasswordForm {
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String password;

    @NotEmpty
    private String confirmPassword;

    public ChangePasswordForm(
            @NotEmpty String oldPassword,
            @NotEmpty String password,
            @NotEmpty String confirmPassword) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
