package project.web;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Project;
import project.system.ProjectModel;

/**
 * Bean responsible for managing the registering of new users on the system.
 */
@Named("signupBean")
@RequestScoped
public class SignupBean implements Serializable {
    private String login, name, email, password, passwordConfirm;
    @Inject private SessionBean sessionBean;
    @Inject private ProjectBean projectBean;
    
    /**
     * Returns new user's login.
     * @return new user's login
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Returns new user's name.
     * @return new user's name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Return new user's email.
     * @return new user's email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Return new user's password.
     * @return new user's password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Return new user's password confirmation
     * @return new user's password confirmation
     */
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    
    /**
     * Sets the new user's login
     * @param login new "new user's login"
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * Sets the new user's name
     * @param name new "new user's name"
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the new user's email
     * @param email new "new user's email"
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Sets the new user's password
     * @param password new "new user's password"
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Sets the new user's password confirmation
     * @param passwordConfirm new "new user's password confirmation"
     */
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    /**
     * Action to create a new user.
     * 
     * This action will create the new user, and if everything runs fine,
     * will redirect him to his profile page. If an error occurs, a message
     * describing the error will be added in the context.
     * 
     * @return action to be executed
     */
    public String createUser() {
        Project project = projectBean.getProject();
        ProjectModel model = project.getModel();
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (model.isLoginTaken(login)) {
            context.addMessage(null, new FacesMessage("Este login já é cadastrado!"));
            return null;
        }
        
        if (model.isEmailTaken(email)) {
            context.addMessage(null, new FacesMessage("Este email já é cadastrado!"));
            return null;
        }
        
        if (!password.equals(passwordConfirm)) {
            context.addMessage(null, new FacesMessage("As senhas não conferem!"));
            return null;
        }
        
        model.addUser(login, password, name, email);
        
        context.addMessage(null, new FacesMessage("O usuário foi criado!"));
        
        if (!sessionBean.logon(login, password))
            throw new IllegalArgumentException("Something very bad happened...");
        
        return "profile?faces-redirect=true";
    }
}
