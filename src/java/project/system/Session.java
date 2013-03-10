package project.system;

/**
 * Represents the session of a currently logged user.
 */
public class Session implements Comparable<Session> {

    private User user;

    /**
     * Constructs a user session.
     *
     * @param user The (logged) user
     */
    public Session(User user) {
        this.user = user;
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
     * Returns a string representation of this session.
     *
     * @return the representation
     */
    @Override
    public String toString() {
        return "[Session for: " + user.toString() + "]";
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
        if (this.user != other.user && (this.user == null || !this.user.equals(other.user))) {
            return false;
        }
        return true;
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
