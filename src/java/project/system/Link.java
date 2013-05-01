package project.system;

import java.io.Serializable;
import project.exceptions.InvalidLinkException;

/**
 * Represents a web link.
 */
public class Link implements Serializable, Comparable<Link> {

    private String path;

    /**
     * Creates a Link.
     *
     * @param path link's path
     */
    public Link(String path) {
        setPath(path);
    }

    /**
     * Returns link's path.
     *
     * @return link's path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set link's path.
     *
     * @param path new path
     */
    public void setPath(String path) {
        if (path == null || path.isEmpty()) {
            throw new InvalidLinkException();
        }
        this.path = path;
    }

    /**
     * Returns link's hashcode.
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return path.hashCode();
    }

    /**
     * Checks if this Link equals another object.
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
        final Link other = (Link) obj;
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of this link.
     *
     * @return the representation
     */
    @Override
    public String toString() {
        return path.toString();
    }

    /**
     * Compares the link to another link.
     *
     * The comparison order is the lexicographical order of the link's path.
     *
     * @param other the other link
     * @return less than 0 if the link should appear first than the other,
     * greater than 0 if it should appear latter, 0 if they are equals.
     */
    @Override
    public int compareTo(Link other) {
        return path.compareTo(other.getPath());
    }
}
