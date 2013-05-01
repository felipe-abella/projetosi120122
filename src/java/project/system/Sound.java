package project.system;

import java.util.ArrayList;
import java.util.List;
import project.exceptions.InvalidCreationDateException;
import project.exceptions.InvalidDateException;
import project.exceptions.InvalidLinkException;

/**
 * Represents a sound that some user posted.
 */
public class Sound implements Comparable<Sound> {

    private Link link;
    private SimpleDate creationDate;
    private User author;
    private List<Tag> tagList;

    /**
     * Constructs a new Sound
     *
     * @param link Link of the sound
     * @param creationDate Creation date of the sound
     * @param author Author of the post
     */
    public Sound(Link link, SimpleDate creationDate, User author) {
        this.link = link;
        this.creationDate = creationDate;
        this.author = author;
        tagList = new ArrayList<Tag>();
    }

    /**
     * Constructs a new Sound
     *
     * @param link Link of the sound
     * @param creationDate Creation date of the sound
     * @param author Author of the post
     * @throws InvalidCreationDateException if the creation date is invalid
     * @throws InvalidLinkException if the link is invalid
     */
    public Sound(String link, String creationDate, User author) {
        this.link = new Link(link);
        try {
            this.creationDate = new SimpleDate(creationDate);
        } catch (InvalidDateException ex) {
            throw new InvalidCreationDateException();
        }
        this.author = author;
        tagList = new ArrayList<Tag>();
    }

    /**
     * Add a new tag to this sound.
     *
     * @param tagName The tag name
     */
    public void addTag(String tagName) {
        tagList.add(new Tag(author, tagName));
    }

    /**
     * Returns the tag list.
     *
     * @return the tag list
     */
    public List<Tag> getTagList() {
        return tagList;
    }

    /**
     * Returns the sound link
     *
     * @return the link
     */
    public Link getLink() {
        return link;
    }

    /**
     * Returns the sound creation date
     *
     * @return the creation date
     */
    public SimpleDate getCreationDate() {
        return creationDate;
    }

    /**
     * Returns the author of the sound post
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Changes the link of this sound
     *
     * @param link the new link
     */
    public void setLink(Link link) {
        this.link = link;
    }

    /**
     * Changes the creation date of this sound
     *
     * @param creationDate the new creation date
     */
    public void setCreationDate(SimpleDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Changes the author of the post of this sound
     *
     * @param author the new author
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Returns the hash code of this sound
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.link != null ? this.link.hashCode() : 0);
        hash = 29 * hash + (this.creationDate != null ? this.creationDate.hashCode() : 0);
        hash = 29 * hash + (this.author != null ? this.author.hashCode() : 0);
        return hash;
    }

    /**
     * Returns whether this sound have the same reference as another object.
     *
     * Two sounds might be treated differently even when they have the same
     * link, creation date and author. This is because a user might double post
     * a link in the same day, so, this method just calls Object's equals.
     *
     * @param obj the other object
     * @return whether this sound is the same as the other object
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * Compare this Sound to another one.
     *
     * The comparison uses link, creationDate and author comparison functions,
     * in this order.
     *
     * @param other The other sound to compare
     * @return less than 0 if this sound should be before the other, 0 if they
     * are equal, or 0 if it should be after
     */
    @Override
    public int compareTo(Sound other) {
        if (link.compareTo(other.getLink()) != 0) {
            return link.compareTo(other.getLink());
        }
        if (creationDate.compareTo(other.getCreationDate()) != 0) {
            return creationDate.compareTo(other.getCreationDate());
        }
        return author.compareTo(other.getAuthor());
    }

    /**
     * Returns a string representation of this Sound
     *
     * @return the representation
     */
    @Override
    public String toString() {
        return "[Sound: link=" + getLink() + ", creationDate=" + getCreationDate() + ", author=" + author + "]";
    }
}
