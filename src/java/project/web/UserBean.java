package project.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.exceptions.InvalidSoundException;
import project.system.Session;
import project.system.Sound;
import project.system.User;

@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private SessionBean sessionBean;
    private User targetUser;

    public UserBean() {
        targetUser = null;
    }

    public UserBean(User targetUser) {
        this.targetUser = targetUser;
    }

    private User getUser() { //TODO: Find a clean way instead of this
        if (targetUser != null) {
            return targetUser;
        }

        /* Testing purposes: */
        //if (sessionBean.getSession() == null) {
        //    sessionBean.logon("a", "a");
        //}

        Session session = sessionBean.getSession();

        if (session == null) {
            return null;
        }
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
        for (User friend : getUser().getSources()) {
            list.add(new UserBean(friend));
        }
        return list;
    }

    public List<SoundBean> getSortedSoundFeed() {
        List<SoundBean> list = new ArrayList<SoundBean>();
        for (Sound sound : getUser().getSortedSoundFeed()) {
            list.add(new SoundBean(sound));
        }
        return list;
    }

    public List<SoundBean> getExtraSoundFeed() {
        List<SoundBean> list = new ArrayList<SoundBean>();
        for (Sound sound : getUser().getExtraSoundFeed()) {
            list.add(new SoundBean(sound));
        }
        return list;
    }

    public String favoriteSound(Sound sound, String updateForm) {
        if (getUser().addFavorite(sound)) {
            FacesContext.getCurrentInstance().addMessage(updateForm,
                    new FacesMessage("Som adicionado na lista de favoritos!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(updateForm,
                    new FacesMessage("O som já está na lista de favoritos!"));
        }
        return null;
    }
}
