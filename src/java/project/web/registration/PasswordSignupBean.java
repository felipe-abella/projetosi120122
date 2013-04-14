package project.web.registration;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.User;
import project.system.authentication.AuthenticationException;
import project.system.authentication.password.PasswordAuth;
import project.system.authentication.password.PasswordAuthChannel;
import project.web.ProjectBean;
import project.web.SessionBean;

/**
 * Bean responsible for managing a password-authenticated account creation.
 */
@Named("passwordSignupBean")
@RequestScoped
public class PasswordSignupBean implements Serializable {

    @Inject
    private ProjectBean projectBean;
    @Inject
    private SessionBean sessionBean;
    @Inject
    private SignupBean signupBean;
    private String password, passwordConfirm;

    /**
     * Return new user's password.
     *
     * @return new user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return new user's password confirmation.
     *
     * @return new user's password confirmation
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    /**
     * Sets the new user's password.
     *
     * @param password new "new user's password"
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the new user's password confirmation.
     *
     * @param passwordConfirm new "new user's password confirmation"
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    /**
     * Action responsible for creating the new account.
     * 
     * @return the action to be executed
     */
    public String createUser() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (signupBean.isPasswordRequired() && (password == null || password.isEmpty())) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Digite uma senha!", null));
            return null;
        }
        
        if (!password.equals(passwordConfirm)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As senhas não conferem!", null));
            return null;
        }
        
        User user = signupBean.createUser();
        
        if (user == null)
            return null;
        
        if (password != null && !password.isEmpty() && signupBean.isPasswordRequired()) {
            PasswordAuth.getInstance().registerUser(user, password);

            try {
                PasswordAuthChannel channel = PasswordAuth.getInstance().getChannel(user);
                channel.enterPassword(password);
                channel.login();
                sessionBean.setSession(projectBean.getProject().openNewSession(user, channel));
            } catch (AuthenticationException ex) {
                throw new IllegalStateException("Something very bad happened...");
            }
        }
        
        return "profile?faces-redirect=true";
    }
}
