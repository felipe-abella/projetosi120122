package project.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import project.system.Session;

/**
 * Bean responsible for managing the logged User's session.
 *
 * This bean encapsulates a Session instance.
 */
@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    @Inject
    private ProjectBean projectBean;
    private Session session = null;

    /**
     * Returns the user's session.
     *
     * @return the user's session
     */
    public Session getSession() {
        return session;
    }

    /**
     * Change the current session.
     *
     * @param newSession the new session
     */
    public void setSession(Session newSession) {
        if (session != null && newSession != session
                && projectBean.getProject().isSessionAlive(session)) {
            throw new IllegalStateException("Leaving an open session!");
        }
        session = newSession;
        if (session != null && !projectBean.getProject().isSessionAlive(session))
            throw new IllegalStateException("Using a dead session!");
    }
}
