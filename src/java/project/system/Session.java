package project.system;

public class Session implements Comparable<Session> {
    private User user;
    
    public Session(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "[Session for: " + user.toString() + "]";
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

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
    
    @Override
    public int compareTo(Session other) {
        return user.compareTo(other.getUser());
    }
}
