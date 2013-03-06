package project.web;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.exceptions.InvalidFollowingException;
import project.exceptions.InvalidLinkException;
import project.system.Link;
import project.system.SimpleDate;
import project.system.Sound;
import project.system.User;

@Named("profileBean")
@SessionScoped
public class ProfileBean implements Serializable {
    @Inject private SessionBean sessionBean;
    @Inject private ProjectBean projectBean;
    private String newSourceLogin;
    private String newPostText;
    
    public ProfileBean() {
    }
    
    public String getNewSourceLogin() {
        return newSourceLogin;
    }
    
    public String getNewPostText() {
        return newPostText;
    }
    
    public void setNewSourceLogin(String newSourceLogin) {
        this.newSourceLogin = newSourceLogin;
    }
    
    public void setNewPostText(String newPostText) {
        this.newPostText = newPostText;
    }
    
    public String clearNewPostText() {
        newPostText = "";
        return null;
    }
    
    public String addNewPost() {
        User user = sessionBean.getSession().getUser();
        
        try {
            user.post(new Sound(new Link(newPostText), new SimpleDate(), user));
        } catch(InvalidLinkException ex) {
            FacesContext.getCurrentInstance().addMessage("newPostForm", new FacesMessage("Link inválido!"));
            return null;
        }
        
        FacesContext.getCurrentInstance().addMessage("newPostForm", new FacesMessage("Link postado!"));
        
        newPostText = "";
        return null;
    }
    
    public String addNewSource() {
        try {
            User user, user2;
            user = sessionBean.getSession().getUser();
            user2 = projectBean.getProject().getModel().findUserByLogin(newSourceLogin);
            if (!projectBean.getProject().getModel().addUserFollowing(user, user2)) {
                FacesContext.getCurrentInstance().addMessage("newSource", new FacesMessage("O usuário já é amigo!"));
                return null;
            }
        } catch (InvalidFollowingException ex) {
            FacesContext.getCurrentInstance().addMessage("newSource", new FacesMessage("Usuário inválido!"));
            return null;
        }
        
        FacesContext.getCurrentInstance().addMessage("newSource", new FacesMessage("Usuário adicionado!"));
            
        newSourceLogin = null;
        return null;
    }
}
