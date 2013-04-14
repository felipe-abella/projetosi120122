package project.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import project.system.authentication.AuthChannel;
import project.system.authentication.Authenticator;
import project.system.authentication.LogoutFailedException;
import project.system.authentication.NotLoggedInException;
import project.system.statistics.Stats;

/**
 * Is the principal controller of the system.
 */
public class Project implements Serializable {

    private static Project instance = null;
    private ProjectModel model;
    private List<Session> sessions;
    private List<Authenticator> authenticators;
    private Stats stats;

    /**
     * Constructs the Project.
     */
    private Project() {
        model = new ProjectModel();
        sessions = new ArrayList<Session>();
        authenticators = new LinkedList<Authenticator>();
        stats = new Stats(this);
    }

    /**
     * Returns the instance of Project.
     *
     * If the project wasn't instantiated before, it will be now.
     *
     * @return the instance
     */
    public static Project getInstance() {
        if (instance == null) {
            instance = new Project();
        }
        return instance;
    }

    /**
     * Returns the project's model.
     *
     * @return the model
     */
    public ProjectModel getModel() {
        return model;
    }

    /**
     * Return the sessions of the Project.
     *
     * @return the session
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Returns a list with the sessions of a user.
     *
     * @param login login of the user
     * @return the sessions
     */
    public List<Session> getSessionsOf(User user) {
        List<Session> result = new ArrayList<Session>();

        for (Session session : sessions) {
            if (session.getUser().equals(user)) {
                result.add(session);
            }
        }

        return result;
    }

    public Session openNewSession(User user, AuthChannel channel) throws NotLoggedInException {
        Session session = new Session(user, channel);
        sessions.add(session);
        return session;
    }
    
    public boolean isSessionAlive(Session session) {
        return sessions.contains(session);
    }

    public void closeSession(Session session) throws LogoutFailedException {
        session.close();
        sessions.remove(session);
    }

    public void registerAuthenticator(Authenticator authenticator) {
        authenticators.add(authenticator);
    }

    /**
     * Returns the Stats for this Project.
     *
     * @return the Stats
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Clears all data.
     *
     * This puts the Project in a state equal to a newly created Project.
     */
    public void clear() {
        model.clear();
        sessions.clear();
        authenticators.clear();
        stats.clear();
    }
}
