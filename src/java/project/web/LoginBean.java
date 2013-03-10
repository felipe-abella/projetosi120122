package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean responsible for managing the login form.
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String login, password;
    @Inject
    private SessionBean sessionBean;

    /**
     * Returns the user's input login.
     *
     * @return the user's input login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the user's input password.
     *
     * @return the user's input password
     */
    public String getPassword() {
        return password;
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
     * Sets the user's input password.
     *
     * @param password the new user's input password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Tries to log on the system using the input login and password.
     *
     * If the login fails, a message informing so will be added to the current
     * context.
     *
     * @return the action to make
     */
    public String logon() {
        if (!sessionBean.logon(login, password)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Login ou Senha inválida!"));
            return null;
        }
        return "profile?faces-redirect=true";
    }
}
