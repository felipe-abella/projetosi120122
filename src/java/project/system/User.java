package project.system;

import project.system.feedsorting.FeedSorter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import project.exceptions.CircleNameTakenException;
import project.exceptions.CircleNotFoundException;
import project.exceptions.InvalidCircleNameException;
import project.exceptions.InvalidEmailException;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidNameException;
import project.exceptions.InvalidSoundException;
import project.system.feedsorting.ChronologicalSourceFeedSorter;

/**
 * Represents a User of the system.
 */
public class User implements Comparable<User> {

    private String login, name, email;
    private List<Sound> postlist;
    private List<User> sources;
    private SortedSet<User> followers;
    private Map<String, Circle> circles;
    private List<Sound> favoriteList;
    private FeedSorter feedSorter;
    private List<Tag> tagList;

    /**
     * Creates a new user.
     *
     * @param login User's login
     * @param name User's name
     * @param email User's email
     * @throws InvalidLoginException if the new login is invalid
     * @throws InvalidNameException if the name is invalid
     */
    public User(String login, String name, String email) {
        setLogin(login);
        setName(name);
        setEmail(email);

        postlist = new LinkedList<Sound>();
        sources = new ArrayList<User>();
        circles = new HashMap<String, Circle>();
        followers = new TreeSet<User>();
        favoriteList = new LinkedList<Sound>();
        feedSorter = new ChronologicalSourceFeedSorter();
        tagList = new ArrayList<Tag>();
    }

    /**
     * Returns the user's login.
     *
     * @return the user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the user's email.
     *
     * @return the user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the feed sorter of this user.
     *
     * The feed sorter is the object responsible for constructs the user main
     * sound feed.
     *
     * @return the feed sorter
     */
    public FeedSorter getFeedSorter() {
        return feedSorter;
    }

    /**
     * Returns the user's sound sources.
     *
     * @return the user's sources
     */
    public List<User> getSources() {
        return sources;
    }

    /**
     * Returns the user's social circles.
     *
     * @return the circles
     */
    public Map<String, Circle> getCircles() {
        return circles;
    }

    /**
     * Returns the list of the followers of this user.
     *
     * @return the followers
     */
    public SortedSet<User> getFollowers() {
        return followers;
    }

    /**
     * Returns the user's post list.
     *
     * @return the user's post list
     */
    public List<Sound> getPostlist() {
        return postlist;
    }

    /**
     * Returns the list of all user's favorite sounds.
     *
     * @return the favorite list
     */
    public List<Sound> getFavoriteList() {
        return favoriteList;
    }

    /**
     * Changes this user's login.
     *
     * @param login the new login
     * @throws InvalidLoginException if the new login is invalid
     */
    public void setLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException();
        }
        this.login = login;
    }

    /**
     * Changes this user's name
     *
     * @param name the new name
     * @throws InvalidNameException if the new name is invalid
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }
        this.name = name;
    }

    /**
     * Changes this user's email
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    /**
     * Changes this user's post list.
     *
     * @param postlist the new post list
     */
    public void setPostlist(List<Sound> postlist) {
        this.postlist = postlist;
    }

    /**
     * Changes this user's feed sorter.
     *
     * @param feedSorter the new feed sorter
     */
    public void setFeedSorter(FeedSorter feedSorter) {
        this.feedSorter = feedSorter;
    }

    /**
     * Add a new tag to a user's tag list.
     *
     * @param tagName The new tag name
     * @return the new tag
     */
    public Tag addTag(String tagName) {
        Tag tag = new Tag(this, tagName);
        tagList.add(tag);
        return tag;
    }

    /**
     * Return the tag list.
     *
     * @return the tag list
     */
    public List<Tag> getTagList() {
        return tagList;
    }

    /**
     * Adds a new circle to the user social circles list.
     *
     * @param circleName the new circle name
     * @throws InvalidCircleNameException if the name is null or empty
     * @throws CircleNameTakenException if there's already a circle with this
     * name
     */
    public void addCircle(String circleName) {
        if (circleName == null || circleName.isEmpty()) {
            throw new InvalidCircleNameException();
        }
        if (circles.get(circleName) != null) {
            throw new CircleNameTakenException();
        }

        circles.put(circleName, new Circle(this, circleName));
    }

    /**
     * Returns the circle with a given name.
     *
     * @param circleName the circle's name
     * @return the circle
     * @throws InvalidCircleNameException if the circle's name is invalid
     * @throws CircleNotFoundException if the circle is not found
     */
    public Circle getCircle(String circleName) {
        if (circleName == null || circleName.isEmpty()) {
            throw new InvalidCircleNameException();
        }
        Circle result = circles.get(circleName);
        if (result == null) {
            throw new CircleNotFoundException();
        }
        return result;
    }

    /**
     * Count how many sources this user have.
     *
     * @return the amount of sources this user have
     */
    public int countSources() {
        return sources.size();
    }

    /**
     * Count how many followers this user have.
     *
     * @return the amount of followers this user have
     */
    public int countFollowers() {
        return followers.size();
    }

    /**
     * Add a new post of this user in the post list
     *
     * @param sound The sound of the post
     */
    public void post(Sound sound) {
        postlist.add(0, sound);
    }

    /**
     * Add a sound to this user's favorite sound list.
     *
     * @param sound The sound to add
     * @return true if successful, false if it was already on the list
     * @throws InvalidSoundException if the sound is invalid
     */
    public boolean addFavorite(Sound sound) {
        if (sound == null) {
            throw new InvalidSoundException();
        }
        if (!favoriteList.contains(sound)) {
            favoriteList.add(0, sound);
            return true;
        }
        return false;
    }

    /**
     * Add a source to the user's source list.
     *
     * @param source the source
     */
    public void addSource(User source) {
        sources.add(source);
    }

    /**
     * Add a followers to the user's follower list.
     *
     * @param follower the follower to add
     */
    public void addFollower(User follower) {
        followers.add(follower);
    }

    /**
     * Returns the sound feed of this user in an unsorted way.
     *
     * The sound feed of a user is the concatenation of the post list of all the
     * sources of this user. This function will return it in its natural form,
     * which contains sounds from the same source grouped together in the same
     * order as the source post list, and sources added latter appears first on
     * this list (like a stack).
     *
     * @return the unsorted sound feed
     */
    public List<Sound> getUnsortedSoundFeed() {
        List<Sound> result = new ArrayList<Sound>();
        for (int i = sources.size() - 1; i >= 0; i--) {
            User source = sources.get(i);
            result.addAll(source.getPostlist());
        }
        return result;
    }

    /**
     * Returns the sound feed of this user sorted with feedSorter.
     *
     * The sound feed of a user is the concatenation of the post list of all the
     * sources of this user. This function will sort the feed using the
     * configured feed sorter before returning.
     *
     * @return the sorted sound feed
     */
    public List<Sound> getSortedSoundFeed() {
        return feedSorter.sortFeed(this, sources);
    }

    /**
     * Returns the extra sound feed of this user.
     *
     * The extra sound feed of a user contains all the posts that this user's
     * sources marked as favorite. This function will return the extra feed in
     * its natural ordering, where posts marked as favorite by the same source
     * will be grouped together, sorted in the same way as in source favorite
     * list, and sources added latter will appear first on this list (like a
     * stack).
     *
     * @return the extra sound feed
     */
    public List<Sound> getExtraSoundFeed() {
        List<Sound> result = new ArrayList<Sound>();
        for (int i = sources.size() - 1; i >= 0; i--) {
            User source = sources.get(i);
            result.addAll(source.getFavoriteList());
        }
        return result;
    }

    /**
     * Compare this user to another one.
     *
     * @param other The other user
     * @return less than 0 if this user name is lexicographically first than the
     * other, 0 if both names are equal, and greater than 0 if it's
     * lexicographically later.
     */
    @Override
    public int compareTo(User other) {
        return name.compareTo(other.getName());
    }

    /**
     * Returns the user's hash code
     *
     * @return the user's hash code
     */
    @Override
    public int hashCode() {
        return login.hashCode();
    }

    /**
     * Returns whether this user equals another object.
     *
     * One user equals another when they have the same login.
     *
     * @param obj the other object
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
        final User other = (User) obj;
        if ((this.login == null) ? (other.login != null) : !this.login.equals(other.login)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this User.
     *
     * @return the representation
     */
    @Override
    public String toString() {
        return "[User, login=" + getLogin() + ", name=" + getName() + "]";
    }
}
