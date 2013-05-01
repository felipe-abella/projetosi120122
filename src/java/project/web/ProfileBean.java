package project.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.autocomplete.AutoComplete;
import project.exceptions.CircleCantAddOwnerException;
import project.exceptions.CircleNameTakenException;
import project.exceptions.CircleNotFoundException;
import project.exceptions.InvalidCircleNameException;
import project.exceptions.InvalidFollowingException;
import project.exceptions.InvalidLinkException;
import project.exceptions.UserAlreadyInCircleException;
import project.system.Circle;
import project.system.Link;
import project.system.SimpleDate;
import project.system.Sound;
import project.system.User;
import project.system.feedsorting.ChronologicalSourceFeedSorter;
import project.system.feedsorting.FavoriteSourcesFeedSorter;
import project.system.feedsorting.FeedSorter;
import project.system.feedsorting.MostFavoritedFeedSorter;
import project.system.recommender.FriendRecommender;

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
    private String newFriendLogin;
    private String newPostText;
    private FeedSorter feedSorters[] = {new ChronologicalSourceFeedSorter(),
        new MostFavoritedFeedSorter(), new FavoriteSourcesFeedSorter(),};
    private FeedSorter feedSorter = null;
    private String feedSourceSetRule = "mainFeed";
    private String friendSetRule = "mainSources";
    private String newCircleName;

    /**
     * Returns the login of the user about to be added to the current friend
     * list.
     *
     * @return the new friend's login
     */
    public String getNewFriendLogin() {
        return newFriendLogin;
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
     * Sets the login of the user about to be added in the current friend list.
     *
     * @param newFriendLogin the new "new source login"
     */
    public void setNewFriendLogin(String newFriendLogin) {
        this.newFriendLogin = newFriendLogin;
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
            FacesContext.getCurrentInstance().addMessage("newPostForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Link inválido!", null));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage("newPostForm", new FacesMessage("Link postado!"));

        newPostText = "";
        return null;
    }

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

    /**
     * Returns the feed source set rule.
     *
     * @return the feed source set rule.
     */
    public String getFeedSourceSetRule() {
        return feedSourceSetRule;
    }

    /**
     * Sets the feed source set rule.
     *
     * @param feedSourceSetRule the new feed source set rule
     */
    public void setFeedSourceSetRule(String feedSourceSetRule) {
        this.feedSourceSetRule = feedSourceSetRule;
        if (!isValidFeedRule(feedSourceSetRule)) {
            throw new IllegalStateException("Invalid feed source set rule");
        }
    }

    private List<Sound> getSoundFeed() {
        User user = sessionBean.getSession().getUser();

        if (feedSourceSetRule.equals("mainFeed")) {
            return sessionBean.getSession().getUser().getSortedSoundFeed();
        } else if (feedSourceSetRule.equals("ownFeed")) {
            return sessionBean.getSession().getUser().getOwnSortedSoundFeed();
        }
        if (feedSourceSetRule.startsWith("circle_")) {
            String circleName = feedSourceSetRule.substring("circle_".length());
            try {
                Circle circle = user.getCircle(circleName);
                return circle.getFeed();
            } catch (InvalidCircleNameException ex) {
                throw new IllegalStateException("The UI circle list contained a invalid name.");
            } catch (CircleNotFoundException ex) {
                /* Likely the circle was removed and the source set rule wasn't
                 * changed.
                 */
                return new ArrayList<Sound>();
            }
        }

        throw new IllegalStateException("Invalid feed source set rule");
    }

    /**
     * Returns the sound feed.
     *
     * The sound feed will be constructed using sources chosen with the source
     * set rule, and then sorted according to the feed sorting rule.
     *
     * @return the sound feed
     */
    public List<SoundBean> getSoundBeanFeed() {
        List<SoundBean> result = new ArrayList<SoundBean>();

        for (Sound sound : getSoundFeed()) {
            result.add(new SoundBean(sound));
        }

        return result;
    }

    /**
     * Returns the friend set rule.
     *
     * @return the friend set rule
     */
    public String getFriendSetRule() {
        return friendSetRule;
    }

    private boolean isValidFeedRule(String feedRule) {
        return feedRule.equals("mainFeed")
                || feedRule.equals("ownFeed")
                || feedRule.startsWith("circle_");
    }

    /**
     * Sets the friend set rule.
     *
     * @param friendSetRule the new friend set rule
     */
    public void setFriendSetRule(String friendSetRule) {
        if (!friendSetRule.equals("mainSources") && !friendSetRule.startsWith("circle_")) {
            throw new IllegalArgumentException("Invalid friendSetRule");
        }
        this.friendSetRule = friendSetRule;
    }

    private List<User> getFriendList() {
        User user = sessionBean.getSession().getUser();

        if (friendSetRule.equals("mainSources")) {
            return user.getSources();
        } else if (friendSetRule.startsWith("circle_")) {
            String circleName = friendSetRule.substring("circle_".length());
            try {
                Circle circle = user.getCircle(circleName);
                return circle.getUsers();
            } catch (InvalidCircleNameException ex) {
                throw new IllegalStateException("The UI circle list contained a invalid name.");
            } catch (CircleNotFoundException ex) {
                /* Likely the circle was removed and the friend set rule wasn't
                 * changed.
                 */
                return new ArrayList<User>();
            }
        }

        throw new IllegalStateException("Invalid friendSetRule");
    }

    /**
     * Returns the friend list, encapsulated by UserBean's.
     *
     * @return the friend list
     */
    public List<UserBean> getFriendBeanList() {
        List<UserBean> result = new ArrayList<UserBean>();

        for (User user : getFriendList()) {
            result.add(new UserBean(user));
        }

        return result;
    }

    /**
     * Adds a new source to the user's source list.
     *
     * @return action to be executed
     */
    public String addNewFriend() {
        User user, user2;

        user = sessionBean.getSession().getUser();
        user2 = projectBean.getProject().getModel().findUserByLogin(newFriendLogin);

        if (newFriendLogin == null || newFriendLogin.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário inválido!", null));
            return null;
        }

        if (user2 == null) {
            FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!", null));
            return null;
        }

        if (friendSetRule.equals("mainSources")) {
            try {
                if (!projectBean.getProject().getModel().addUserFollowing(user, user2)) {
                    FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_WARN, "O usuário já é amigo!", null));
                    return null;
                }
            } catch (InvalidFollowingException ex) {
                FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário inválido!", null));
                return null;
            }
        } else if (friendSetRule.startsWith("circle_")) {
            String circleName = friendSetRule.substring("circle_".length());
            try {
                Circle circle = user.getCircle(circleName);
                circle.addUser(user2);
            } catch (UserAlreadyInCircleException ex) {
                FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_WARN, "O usuário já está no círculo!", null));
                return null;
            } catch (CircleCantAddOwnerException ex) {
                FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário inválido!", null));
                return null;
            } catch (InvalidCircleNameException ex) {
                throw new IllegalStateException("The UI circle list contained a invalid name.");
            } catch (CircleNotFoundException ex) {
                /* Likely the circle was removed and the friend set rule wasn't
                 * changed.
                 */
                FacesContext.getCurrentInstance().addMessage("friendList",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Círculo inexistente!", "Círculo inexistente!"));

                return null;
            }
        } else {
            throw new IllegalStateException("Invalid friendSetRule");
        }

        FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage("Usuário adicionado!"));

        newFriendLogin = null;
        return null;
    }

    /**
     * Returns the name of the new circle.
     *
     * @return the name of the new circle
     */
    public String getNewCircleName() {
        return newCircleName;
    }

    /**
     * Sets the name of the new circle
     *
     * @param newCircleName the new name for the new circle
     */
    public void setNewCircleName(String newCircleName) {
        this.newCircleName = newCircleName;
    }

    /**
     * Adds a new circle to the user's circles.
     *
     * @return action to be executed
     */
    public String addNewCircle() {
        String name = newCircleName;
        User user = sessionBean.getSession().getUser();

        try {
            user.addCircle(name);
        } catch (InvalidCircleNameException ex) {
            FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nome inválido!", null));
            return null;
        } catch (CircleNameTakenException ex) {
            FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_WARN, "O círculo já existe!", null));
            return null;
        }

        FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage("Círculo criado!"));

        newCircleName = "";
        return null;
    }

    /**
     * Returns the list of new friend suggestions.
     *
     * @return the list
     */
    public List<User> getNewFriendSuggestions() {
        User user = sessionBean.getSession().getUser();
        List<User> excludedList = getFriendList();

        return FriendRecommender.getFriendRecommendations(user, excludedList);
    }

    /**
     * Returns the list of the logins of the new friend suggestions.
     *
     * @param query Will be ignored
     * @return the list
     */
    public List<String> getNewFriendSuggestionsLogins(String query) {
        List<User> suggestions = getNewFriendSuggestions();
        List<String> result = new ArrayList<String>();

        for (User potFriend : suggestions) {
            result.add(potFriend.getLogin());
        }

        return result;
    }

    /**
     * Action for when the user asks for new friend suggestions.
     *
     * @return null
     */
    public String getNewFriendSuggestionsAction() {
        if (getNewFriendSuggestions().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("friendList", new FacesMessage(FacesMessage.SEVERITY_WARN, "Nenhuma sugestão!", null));
        }
        return null;
    }
}
