package project.system.authentication.password;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.system.Project;
import project.system.Session;
import project.system.User;
import project.system.authentication.AuthenticationException;
import project.system.authentication.Authenticator;

public class PasswordAuth implements Authenticator {

    private static PasswordAuth instance = null;
    private Map<User, String> passwords;

    private PasswordAuth() {
        passwords = new HashMap<User, String>();
    }

    public static PasswordAuth getInstance() {
        if (instance == null) {
            Project project = Project.getInstance();
            instance = new PasswordAuth();
            project.registerAuthenticator(instance);
        }
        return instance;
    }

    public boolean isRegistered(User user) {
        return passwords.containsKey(user);
    }
    
    public void registerUser(User user, String password) {
        passwords.put(user, password);
    }

    public void setPassword(User user, String password) {
        if (!isRegistered(user)) {
            registerUser(user, password);
        } else {
            passwords.put(user, password);
        }
    }

    @Override
    public PasswordAuthChannel getChannel(User user) {
        return new PasswordAuthChannel(user);
    }

    public boolean havePassword(User user) {
        return passwords.containsKey(user);
    }

    public boolean checkPassword(User user, String password) {
        if (!passwords.containsKey(user)) {
            return false;
        }
        return passwords.get(user).equals(password);
    }
    
    public static Session openNewSession(User user, String password) {
        try {
            PasswordAuthChannel channel = PasswordAuth.getInstance().getChannel(user);
            channel.enterPassword(password);
            channel.login();
            return Project.getInstance().openNewSession(user, channel);
        } catch (AuthenticationException ex) {
            return null;
        }
    }
}
