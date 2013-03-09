package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String login, password;
    @Inject private SessionBean sessionBean;

    public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String logon() {
        if (!sessionBean.logon(login, password)) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Login ou Senha inválida!"));
            return null;
        }
        return "profile?faces-redirect=true";
    }
}
