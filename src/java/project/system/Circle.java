package project.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import project.exceptions.CircleCantAddOwnerException;
import project.exceptions.UserAlreadyInCircleException;
import project.system.feedsorting.FeedSorter;

/**
 * Represents a social circle as seen by a defined user.
 */
public class Circle implements Serializable {

    private User owner;
    private String name;
    private List<User> users;

    /**
     * Creates a new circle.
     *
     * @param owner The owner of the social circle
     * @param name The circle's name
     */
    public Circle(User owner, String name) {
        this.owner = owner;
        this.name = name;
        users = new ArrayList<User>();
    }

    /**
     * Returns the circle's owner.
     *
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Returns the circle's name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of the users in the circle except the owner.
     *
     * @return the list
     */
    public List<User> getUsers() {
        return users;
    }
    
    /**
     * Returns whether this circle contains a given user.
     * 
     * @param user the user
     * @return if it belongs to the circle
     */
    public boolean containsUser(User user) {
        return users.contains(user);
    }

    /**
     * Adds a user to this circle.
     *
     * @param user the user
     * @throws UserAlreadyInCircleException if the user is already in the circle
     * @throws CircleCantAddOwnerException if the user is the circle's owner
     */
    public void addUser(User user) {
        if (users.contains(user)) {
            throw new UserAlreadyInCircleException();
        }
        if (user.equals(owner)) {
            throw new CircleCantAddOwnerException();
        }
        users.add(0, user);
    }
    
    /**
     * Returns the feed for this circle.
     * 
     * @return the feed
     */
    public List<Sound> getFeed() {
        FeedSorter sorter = owner.getFeedSorter();
        
        return sorter.sortFeed(owner, users);
    }

    /**
     * Checks whether this object equals another.
     * 
     * @param obj The another object
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
        final Circle other = (Circle) obj;
        if (this.owner != other.owner && (this.owner == null || !this.owner.equals(other.owner))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the hash code of this object.
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.owner != null ? this.owner.hashCode() : 0);
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    /**
     * Returns a string representation of the Circle.
     * 
     * @return the string
     */
    @Override
    public String toString() {
      return "[Circle, owner=" + owner.getLogin() + ", name=" + name + "]";
    }
}
