package project.system;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidPasswordException;
import project.exceptions.UserNotFoundException;

/**
 * Is the principal controller of the system.
 */
public class Project implements Serializable {

    private Set<Session> sessions;
    private Map<User, Session> userSession;
    private ProjectModel model;

    /**
     * Constructs the Project.
     */
    public Project() {
        sessions = new TreeSet<Session>();
        userSession = new TreeMap<User, Session>();
        model = new ProjectModel();
    }

    /**
     * Returns project's model.
     *
     * @return the model
     */
    public ProjectModel getModel() {
        return model;
    }

    /**
     * Creates a new user's session.
     *
     * This function should be called when an user logs on the system.
     *
     * @param login User's login
     * @param password User's password
     * @return User's session
     */
    public Session login(String login, String password) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }

        User user = model.findUserByLogin(login);

        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        Session result = userSession.get(user);
        if (result == null) {
            result = new Session(user);
            sessions.add(result);
            userSession.put(user, result);
        }

        return result;
    }

    /**
     * Logouts a user, thus invalidating its session.
     *
     * @param login Login of the user to logout
     */
    public void logout(String login) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }

        User user = model.findUserByLogin(login);

        if (user == null) {
            throw new InvalidLoginException();
        }

        Session session = userSession.get(user);
        if (session != null) {
            sessions.remove(session);
            userSession.remove(user);
        }
    }

    /**
     * Returns the session of a user.
     *
     * @param login login of the user
     * @return the session
     */
    public Session getSessionOf(String login) {
        return userSession.get(model.findUserByLogin(login));
    }

    /**
     * Clears all data.
     * 
     * This put the Project in a state equal to a newly created Project.
     */
    public void clear() {
        userSession.clear();
        sessions.clear();
        model.clear();
    }
}
