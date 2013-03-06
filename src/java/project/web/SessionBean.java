package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Project;
import project.system.Session;
import project.system.User;

@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {
    @Inject private ProjectBean projectBean;
    private Session session = null;
    
    public SessionBean() {
    }
    
    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
    }
    
    public void logout() {
        session = null;
    }
    
    public String logoutAction() {
        logout();
        return "index?faces-redirect=true";
    }
    
    public boolean logon(String login, String password) {
        User user = projectBean.getProject().getModel().findUserByLogin(login);
        
        if (user == null)
            return false;
        if (!user.getPassword().equals(password))
            return false;
        
        if (session != null)
            logout();
        session = new Session(user);
        
        return true;
    }
}
