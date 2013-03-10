package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Project;
import project.system.Session;
import project.system.User;

/**
 * Bean responsible for managing the logged User's session.
 *
 * This bean encapsulates a Session instance.
 */
@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    @Inject
    private ProjectBean projectBean;
    private Session session = null;

    /**
     * Returns the user's session.
     *
     * @return the user's session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Change the current session.
     *
     * @param session the new session
     */
    public void setSession(Session session) {
        this.session = session;
    }

    /**
     * Logout the current user.
     *
     * After this, the current session will be null
     */
    public void logout() {
        session = null;
    }

    /**
     * Logout and return the action to be made.
     *
     * @return the action
     */
    public String logoutAction() {
        logout();
        return "index?faces-redirect=true";
    }

    /**
     * Tries to log the user with a given login using a given password.
     * @param login User's login
     * @param password User's password
     * @return true if the login was successful, false otherwise
     */
    public boolean logon(String login, String password) {
        User user = projectBean.getProject().getModel().findUserByLogin(login);

        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }

        if (session != null) {
            logout();
        }
        session = new Session(user);

        return true;
    }
}
