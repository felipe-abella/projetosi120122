package project.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Session;
import project.system.Sound;
import project.system.User;

@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {
    @Inject private SessionBean sessionBean;
    private User targetUser; // TODO: THIS IS TERRIBLY, FIX IT USING CONVERSATIONS, OR ANYTHING LIKE THAT
// INVALIDATE SESSION DURING LOGOUT    
    public UserBean() {
        targetUser = null;
    }
    
    public UserBean(User targetUser) {
        this.targetUser = targetUser;
    }
    
    private User getUser() {
        if (targetUser != null)
            return targetUser;
        
        /* Testing purposes: */
        /*if (sessionBean.getSession() == null)
            sessionBean.logon("a", "a");*/
        
        Session session = sessionBean.getSession();
        
        if (session == null)
            return null;
        return session.getUser();
    }
    
    public String getName() {
        return getUser().getName();
    }
    
    public String getLogin() {
        return getUser().getLogin();
    }
    
    public String getEmail() {
        return getUser().getEmail();
    }
    
    public List<UserBean> getSourceList() {
        List<UserBean> list = new ArrayList<UserBean>();
        for (User friend: getUser().getSources())
            list.add(new UserBean(friend));
        return list;
    }
    
    public List<SoundBean> getSoundFeed() {
        List<SoundBean> list = new ArrayList<SoundBean>();
        for (Sound sound: getUser().getSoundFeed())
            list.add(new SoundBean(sound));
        return list;
    }
}
