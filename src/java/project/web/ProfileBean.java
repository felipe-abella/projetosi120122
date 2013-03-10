package project.web;

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
import project.system.feedsorting.ChronologicalSourceFeedSorter;
import project.system.feedsorting.FavoriteSourcesFeedSorter;
import project.system.feedsorting.FeedSorter;
import project.system.feedsorting.MostFavoritedFeedSorter;

/**
 * Manages forms and operations made on a user's profile page.
 */
@Named("profileBean")
@SessionScoped
public class ProfileBean implements Serializable {

    @Inject
    private SessionBean sessionBean;
    @Inject
    private ProjectBean projectBean;
    private String newSourceLogin;
    private String newPostText;

    /**
     * Returns the login of the user about to be added as a new source.
     *
     * @return the new source's login
     */
    public String getNewSourceLogin() {
        return newSourceLogin;
    }

    /**
     * Returns the text of the new post.
     *
     * @return new post text
     */
    public String getNewPostText() {
        return newPostText;
    }

    /**
     * Sets the login of the user about to be added as a new source.
     *
     * @param newSourceLogin the new "new source login"
     */
    public void setNewSourceLogin(String newSourceLogin) {
        this.newSourceLogin = newSourceLogin;
    }

    /**
     * Sets the new post text.
     *
     * @param newPostText new "new post text"
     */
    public void setNewPostText(String newPostText) {
        this.newPostText = newPostText;
    }

    /**
     * Clears new post text.
     *
     * @return action to be executed
     */
    public String clearNewPostText() {
        newPostText = "";
        return null;
    }

    /**
     * Adds a new post in the user's post list.
     *
     * @return action to be executed
     */
    public String addNewPost() {
        User user = sessionBean.getSession().getUser();

        try {
            user.post(new Sound(new Link(newPostText), new SimpleDate(), user));
        } catch (InvalidLinkException ex) {
            FacesContext.getCurrentInstance().addMessage("newPostForm", new FacesMessage("Link inválido!"));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage("newPostForm", new FacesMessage("Link postado!"));

        newPostText = "";
        return null;
    }

    /**
     * Adds a new source to the user's source list.
     *
     * @return action to executed
     */
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
    private FeedSorter feedSorters[] = {new ChronologicalSourceFeedSorter(),
        new MostFavoritedFeedSorter(), new FavoriteSourcesFeedSorter(),};
    private FeedSorter feedSorter = null;

    private FeedSorter getFeedSorterFromRule(String rule) {
        for (FeedSorter sorter : feedSorters) {
            if (sorter.getRuleName().equals(rule)) {
                return sorter;
            }
        }
        return null;
    }

    /**
     * Sets the main feed sorter rule.
     *
     * @param rule rule to be set
     */
    public void setFeedSorterRule(String rule) {
        this.feedSorter = getFeedSorterFromRule(rule);
        if (this.feedSorter != null) {
            sessionBean.getSession().getUser().setFeedSorter(feedSorter);
        }
    }

    /**
     * Returns the main feed sorter rule.
     *
     * @return sorter rule
     */
    public String getFeedSorterRule() {
        if (feedSorter == null) {
            feedSorter = getFeedSorterFromRule("chronologicalSource");
        }
        return feedSorter.getRuleName();
    }
}
