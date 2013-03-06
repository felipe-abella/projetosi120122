package project.web;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Project;
import project.system.ProjectModel;

@Named("signupBean")
@RequestScoped
public class SignupBean implements Serializable {
    private String login, name, email, password, passwordConfirm;
    @Inject private SessionBean sessionBean;
    @Inject private ProjectBean projectBean;
    
    public String getLogin() {
        return login;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getPasswordConfirm() {
        return passwordConfirm;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    public String createUser() {
        Project project = projectBean.getProject();
        ProjectModel model = project.getModel();
        FacesContext context = FacesContext.getCurrentInstance();
        
        if (model.isLoginTaken(login)) {
            System.out.println("Bla: " + project.getModel().getUsers().toString());
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
