package project.system;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import project.exceptions.EmailTakenException;
import project.exceptions.InvalidFollowingException;
import project.exceptions.LoginTakenException;

/**
 * Stores all persistent data that the Project have.
 */
public class ProjectModel implements Serializable {

    private Set<User> users;
    private Map<String, User> logins;
    private Map<String, User> emails;

    /**
     * Constructs a new ProjectModel.
     */
    public ProjectModel() {
        users = new HashSet<User>();
        logins = new HashMap<String, User>();
        emails = new HashMap<String, User>();
    }

    /**
     * Clear all data.
     */
    public void clear() {
        users.clear();
        logins.clear();
        emails.clear();
    }

    /**
     * Return project user list.
     *
     * @return user list
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Add a new user at the model.
     *
     * @param login User's login
     * @param name User's name
     * @param email User's email
     * @throws LoginTakenException if the login is already taken
     * @throws EmailTakenException if the email is already taken
     * @return the added user
     */
    public User addUser(String login, String name, String email) {
        if (isLoginTaken(login)) {
            throw new LoginTakenException();
        }
        if (isEmailTaken(email)) {
            throw new EmailTakenException();
        }
        User user = new User(login, name, email);
        users.add(user);
        logins.put(login, user);
        emails.put(email, user);
        
        return user;
    }

    /**
     * Checks if a login is already taken.
     *
     * @param login The login to check
     * @return whether it's taken
     */
    public boolean isLoginTaken(String login) {
        return logins.containsKey(login);
    }

    /**
     * Checks if a email is already taken.
     *
     * @param email The email to check
     * @return whether it's taken
     */
    public boolean isEmailTaken(String email) {
        return emails.containsKey(email);
    }

    /**
     * Returns the user of a given login.
     *
     * @param login User's login
     * @return the user, or null if not found
     */
    public User findUserByLogin(String login) {
        return logins.get(login);
    }

    /**
     * Add a source-follower relation where user follows user2.
     *
     * @param user User that will follow user2 (Follower)
     * @param user2 User that will be followed by user (Source)
     * @return true if it was successful, false if the relation already exists
     * @throws InvalidFollowingException if users are equal, or one of them is
     * null
     */
    public boolean addUserFollowing(User user, User user2) {
        if (user == user2 || user == null || user2 == null) {
            throw new InvalidFollowingException();
        }
        if (user.getSources().contains(user2)) {
            return false;
        }

        user.addSource(user2);
        user2.addFollower(user);

        return true;
    }
}
