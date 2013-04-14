package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.authentication.password.PasswordAuth;

/**
 * Bean responsible for supporting the Settings page.
 */
@Named("settingsBean")
@SessionScoped
public class SettingsBean implements Serializable {

    @Inject
    private UserBean userBean;
    private String newPassword;
    private String confirmNewPassword;

    /**
     * Returns the new password.
     *
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword the new "new password"
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Returns the new password confirmation.
     *
     * @return the new password confirmation
     */
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    /**
     * Sets the new password confirmation.
     *
     * @param confirmNewPassword the new "new password confirmation"
     */
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    /**
     * Updates the user data with the new data.
     *
     * @return the action to execute
     */
    public String update() {
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!newPassword.equals(confirmNewPassword)) {
                FacesContext.getCurrentInstance().addMessage("settings", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senhas não conferem!", "Senhas não conferem!"));
                return null;
            }

            PasswordAuth.getInstance().setPassword(userBean.getUser(), newPassword);
        }

        FacesContext.getCurrentInstance().addMessage("settings", new FacesMessage("Dados atualizados!"));

        return null;
    }

    /**
     * Redirects the user to its profile.
     *
     * @return the action to execute
     */
    public String goToProfile() {
        return "profile?faces-redirect=true";
    }
}
