package project.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Session;
import project.system.Sound;
import project.system.User;

/**
 * Bean responsible for users manipulation.
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private SessionBean sessionBean;
    private User targetUser;

    /**
     * Creates a UserBean that manages the logged user.
     */
    public UserBean() {
        targetUser = null;
    }

    /**
     * Creates a UserBean that manages a specific user.
     *
     * @param targetUser the specific user, or null for the logged user
     */
    public UserBean(User targetUser) {
        this.targetUser = targetUser;
    }

    /**
     * Returns the user managed by this bean.
     *
     * @return the user
     */
    public User getUser() { //TODO: Find a clean way instead of this
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

    /**
     * Returns the user's name
     *
     * @return user's name
     */
    public String getName() {
        return getUser().getName();
    }

    /**
     * Returns the user's login
     *
     * @return user's login
     */
    public String getLogin() {
        return getUser().getLogin();
    }

    /**
     * Returns the user's email
     *
     * @return the user's email
     */
    public String getEmail() {
        return getUser().getEmail();
    }

    /**
     * Returns the user's source list.
     *
     * Each source will be encapsulated in a UserBean, and the return will be a
     * list of them.
     *
     * @return user's source list
     */
    public List<UserBean> getSourceList() {
        List<UserBean> list = new ArrayList<UserBean>();
        for (User friend : getUser().getSources()) {
            list.add(new UserBean(friend));
        }
        return list;
    }

    /**
     * Returns the user's sorted sound feed.
     *
     * Each sound will be encapsulated in a SoundBean, and the return will be a
     * list of them.
     *
     * @return user's sorted sound feed
     */
    public List<SoundBean> getSortedSoundFeed() {
        List<SoundBean> list = new ArrayList<SoundBean>();
        for (Sound sound : getUser().getSortedSoundFeed()) {
            list.add(new SoundBean(sound));
        }
        return list;
    }

    /**
     * Returns the user's extra sound feed.
     *
     * Each sound will be encapsulated in a SoundBean, and the return will be a
     * list of them.
     *
     * @return user's extra sound feed
     */
    public List<SoundBean> getExtraSoundFeed() {
        List<SoundBean> list = new ArrayList<SoundBean>();
        for (Sound sound : getUser().getExtraSoundFeed()) {
            list.add(new SoundBean(sound));
        }
        return list;
    }

    /**
     * Add a sound to the user's favorite list.
     *
     * @param sound Sound to be added
     * @param updateForm h:form id that initiated the action
     *
     * A message indicating the operation result will be sent for the updateForm
     *
     * @return action to execute
     */
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
