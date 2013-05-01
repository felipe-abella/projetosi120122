package project.system.authentication.facebook;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import project.system.Project;
import project.system.User;
import project.system.authentication.Authenticator;

/**
 * Implements an authentication that logs in using Facebook accounts.
 */
public class FacebookAuth implements Authenticator, Serializable {

    private static FacebookAuth instance = null;
    private Map<User, String> userFaceid;
    private Map<String, User> faceidUser;

    private FacebookAuth() {
        userFaceid = new HashMap<User, String>();
        faceidUser = new HashMap<String, User>();
    }

    /**
     * Returns the singleton instance of this Authenticator.
     *
     * @return the instance
     */
    public static FacebookAuth getInstance() {
        if (instance == null) {
            FacebookAuth auth = new FacebookAuth();
            Project.getInstance().registerAuthenticator(auth);
            instance = auth;
        }
        return instance;
    }

    /**
     * Creates a Facebook authentication channel for a user.
     *
     * @param user the user
     * @return the channel
     */
    @Override
    public FacebookAuthChannel getChannel(User user) {
        return new FacebookAuthChannel(user);
    }

    /**
     * Registers a facebook authentication of a user.
     *
     * @param user the user
     * @param faceid the facebook account id
     * @return if it was successful, if not, the user or the id was otherwise
     * associated
     */
    public boolean register(User user, String faceid) {
        if (!isRegistered(user) && !isRegistered(faceid)) {
            userFaceid.put(user, faceid);
            faceidUser.put(faceid, user);
            return true;
        }
        return false;
    }

    /**
     * Returns if a user is registered for facebook authentication.
     *
     * @param user the user
     * @return if he's registered
     */
    public boolean isRegistered(User user) {
        return userFaceid.containsKey(user);
    }

    /**
     * Returns the facebook id associated to a user.
     *
     * @param user the user
     * @return the facebook id
     */
    public String getFaceid(User user) {
        return userFaceid.get(user);
    }

    /**
     * Returns if a facebook id is registered (i.e., associated to any user)
     *
     * @param faceid the facebook id
     * @return if it's registered
     */
    public boolean isRegistered(String faceid) {
        return faceidUser.containsKey(faceid);
    }

    /**
     * Returns the user associated to a facebook id.
     *
     * @param faceid the facebook id
     * @return the user
     */
    public User getUser(String faceid) {
        return faceidUser.get(faceid);
    }
}
