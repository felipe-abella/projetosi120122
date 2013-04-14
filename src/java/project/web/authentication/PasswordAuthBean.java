package project.web.authentication;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Session;
import project.system.User;
import project.system.authentication.AuthenticationException;
import project.system.authentication.password.NoPasswordException;
import project.system.authentication.password.PasswordAuth;
import project.system.authentication.password.PasswordAuthChannel;
import project.web.ProjectBean;
import project.web.SessionBean;

/**
 * Bean responsible for managing the login form.
 */
@Named("passwordAuthBean")
@RequestScoped
public class PasswordAuthBean implements Serializable {

    @Inject
    private ProjectBean projectBean;
    @Inject
    private SessionBean sessionBean;
    @Inject
    private AuthBean authBean;
    private String password;

    /**
     * Returns the user's input password.
     *
     * @return the user's input password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's input password.
     *
     * @param password the new user's input password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Tries to sign in the system using the input login and password.
     *
     * If the login fails, a message informing so will be added to the current
     * context.
     *
     * @return the action to execute
     */
    public String signin() {
        FacesContext context = FacesContext.getCurrentInstance();

        User user = projectBean.getProject().getModel().findUserByLogin(authBean.getLogin());

        if (user == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login inválido!", null));
            return null;
        }

        try {
            PasswordAuthChannel channel = PasswordAuth.getInstance().getChannel(user);
            channel.enterPassword(password);
            channel.login();

            Session session = projectBean.getProject().openNewSession(user, channel);
            sessionBean.setSession(session);
        } catch(NoPasswordException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O usuário não possui senha registrada!", null));
            return null;
        } catch (AuthenticationException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha inválida!", null));
            return null;
        }

        return "profile?faces-redirect=true";
    }
}
