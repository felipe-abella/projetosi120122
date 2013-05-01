package project.system;

import java.io.Serializable;
import project.system.authentication.AuthChannel;
import project.system.authentication.AuthChannelState;
import project.system.authentication.LogoutFailedException;
import project.system.authentication.NotLoggedInException;

/**
 * Represents the session of a currently logged user.
 */
public class Session implements Comparable<Session>, Serializable {

    private User user;
    private AuthChannel channel;

    /**
     * Constructs a user session.
     *
     * @param user The (logged) user
     * @param channel The authentication channel
     * @throws NotLoggedInException when the auth channel is closed
     */
    Session(User user, AuthChannel channel) throws NotLoggedInException {
        this.user = user;
        this.channel = channel;

        if (channel.getState() == AuthChannelState.CLOSED) {
            throw new NotLoggedInException();
        }
    }

    /**
     * Returns the user of this session.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the authorization channel.
     *
     * @return the auth channel
     */
    public AuthChannel getChannel() {
        return channel;
    }

    /**
     * Closes this session.
     *
     * This method will close the authentication channel, thus invalidating this
     * session, all references to this session must be removed after this method
     * invocation.
     *
     * @throws LogoutFailedException when failed to logout cleanly, but the
     * session should be considered closed anyway.
     */
    public void close() throws LogoutFailedException {
        channel.logout();
    }

    /**
     * Returns a string representation of this session.
     *
     * @return the representation
     */
    @Override
    public String toString() {
        return "[Session for: " + user.toString() + ", channel: " + channel.toString() + "]";
    }

    /**
     * Returns the hash code of this session.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return user.hashCode();
    }

    /**
     * Check whether this session equals another the object.
     *
     * A session will equals another if they have the same user and channel
     * <b>reference</b>.
     *
     * @param obj the another object
     * @return whether they are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Session other = (Session) obj;
        return user == other.user && channel == other.channel;
    }

    /**
     * Compare this session with another one.
     *
     * The comparison criterion will be the same as User's comparison.
     *
     * @param other The other session
     * @return less than 0, equal to 0, or greater to zero if it compares less,
     * equal or greater than the other session
     */
    @Override
    public int compareTo(Session other) {
        return user.compareTo(other.getUser());
    }
}
