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
     * @param user user whose sessions will be returned
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

    /**
     * Opens a new session and add to the session list.
     *
     * @param user owner of the session
     * @param channel the channel used to authenticate the user
     * @return the new created session
     * @throws NotLoggedInException if the channel is not open
     */
    public Session openNewSession(User user, AuthChannel channel) throws NotLoggedInException {
        Session session = new Session(user, channel);
        sessions.add(session);
        return session;
    }

    /**
     * Returns if a session is alive.
     *
     * A session is alive if it's in the list of sessions, otherwise it's
     * considered dead and all references to it should be removed!
     *
     * @param session session to check if is alive
     * @return if the session is alive
     */
    public boolean isSessionAlive(Session session) {
        return sessions.contains(session);
    }

    /**
     * Closes a session.
     *
     * The session will be removed from the list and be considered dead
     * (regardless if the auth channel was properly closed or not).
     *
     * @param session the session to close
     * @throws LogoutFailedException if the auth channel couldn't be closed
     */
    public void closeSession(Session session) throws LogoutFailedException {
        sessions.remove(session);
        session.close();
    }

    /**
     * Registers a new authenticator.
     *
     * @param authenticator the authenticator
     */
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
    
    /**
     * Closes all open sessions.
     */
    public void clearSessions() {
        sessions.clear();
    }
}
