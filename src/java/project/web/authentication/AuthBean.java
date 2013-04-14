package project.web.authentication;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Session;
import project.system.authentication.LogoutFailedException;
import project.web.ProjectBean;
import project.web.SessionBean;

/**
 * Bean responsible for managing general authentication behavior.
 */
@Named("authBean")
@RequestScoped
public class AuthBean implements Serializable {

    @Inject
    private ProjectBean projectBean;
    @Inject
    private SessionBean sessionBean;
    private String login;

    /**
     * Returns the user's input login.
     *
     * @return the user's input login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the user's input login.
     *
     * @param login the new user's input login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Logout the current user.
     *
     * After this, the current session will be null
     */
    public void logout() {
        Session session = sessionBean.getSession();
        if (session != null) {
            try {
                projectBean.getProject().closeSession(session);
            } catch (LogoutFailedException ex) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "O logout precisou ser realizado de forma forçada!", null));
            }
        }
        sessionBean.setSession(null);
    }

    /**
     * Logout and return the action to be executed.
     *
     * @return the action
     */
    public String logoutAction() {
        logout();
        return "index?faces-redirect=true";
    }
}
