package project.system.authentication.facebook;

import java.util.HashMap;
import java.util.Map;
import project.system.Project;
import project.system.User;
import project.system.authentication.Authenticator;

public class FacebookAuth implements Authenticator {

    private static FacebookAuth instance = null;
    private Map<User, String> userFaceid;
    private Map<String, User> faceidUser;

    private FacebookAuth() {
        userFaceid = new HashMap<User, String>();
        faceidUser = new HashMap<String, User>();
    }

    public static FacebookAuth getInstance() {
        if (instance == null) {
            FacebookAuth auth = new FacebookAuth();
            Project.getInstance().registerAuthenticator(auth);
            instance = auth;
        }
        return instance;
    }

    @Override
    public FacebookAuthChannel getChannel(User user) {
        return new FacebookAuthChannel(user);
    }

    public boolean register(User user, String faceid) {
        if (!isRegistered(user) && !isRegistered(faceid)) {
            userFaceid.put(user, faceid);
            faceidUser.put(faceid, user);
            return true;
        }
        return false;
    }
    
    public boolean isRegistered(User user) {
        return userFaceid.containsKey(user);
    }

    public String getFaceid(User user) {
        return userFaceid.get(user);
    }
    
    public boolean isRegistered(String faceid) {
        return faceidUser.containsKey(faceid);
    }
    
    public User getUser(String faceid) {
        return faceidUser.get(faceid);
    }
}
