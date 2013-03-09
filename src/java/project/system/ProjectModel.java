package project.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import project.exceptions.EmailTakenException;
import project.exceptions.InvalidFollowingException;
import project.exceptions.LoginTakenException;

public class ProjectModel implements Serializable {
    private Set<User> users;
    private Map<String, User> logins;
    private Map<String, User> emails;
    
    public ProjectModel() {
        users = new HashSet<User>();
        logins = new HashMap<String, User>();
        emails = new HashMap<String, User>();
    }
    
    public void clear() {
        users.clear();
        logins.clear();
        emails.clear();
    }
    
    public Set<User> getUsers() {
        return users;
    }

    public void addUser(String login, String password, String name, String email) {
        if (isLoginTaken(login))
            throw new LoginTakenException();
        if (isEmailTaken(email))
            throw new EmailTakenException();
        User user = new User(login, password, name, email);
        users.add(user);
        logins.put(login, user);
        emails.put(email, user);
    }

    public boolean isLoginTaken(String login) {
        return logins.containsKey(login);
    }
    
    public boolean isEmailTaken(String email) {
        return emails.containsKey(email);
    }
    
    public User findUserByLogin(String login) {
        return logins.get(login);
    }

    public boolean addUserFollowing(User user, User user2) {
        if (user == user2 || user == null || user2 == null)
            throw new InvalidFollowingException();
        if (user.getSources().contains(user2))
            return false;
        
        user.addSource(user2);
        user2.addFollower(user);
        
        return true;
    }
}
