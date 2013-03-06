package project.system;

import java.util.Date;
import java.util.GregorianCalendar;
import project.exceptions.InvalidCreationDateException;
import project.exceptions.InvalidDateException;

public class Sound implements Comparable<Sound> {
    private Link link;
    private SimpleDate creationDate;
    private User author;
    
    public Sound(Link link, SimpleDate creationDate, User author) {
        this.link = link;
        this.creationDate = creationDate;
        this.author = author;
    }
    
    public Sound(String link, String creationDate, User author) {
        this.link = new Link(link);
        try {
            this.creationDate = new SimpleDate(creationDate);
        } catch (InvalidDateException ex) {
            throw new InvalidCreationDateException();
        }
        this.author = author;
    }
    
    public Link getLink() {
        return link;
    }
    
    public SimpleDate getCreationDate() {
        return creationDate;
    }
    
    public User getAuthor() {
        return author;
    }
    
    public void setLink(Link link) {
        this.link = link;
    }
    
    public void setCreationDate(SimpleDate creationDate) {
        this.creationDate = creationDate;
    }
    
    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sound other = (Sound) obj;
        if (this.link != other.link && (this.link == null || !this.link.equals(other.link))) {
            return false;
        }
        if (this.creationDate != other.creationDate && (this.creationDate == null || !this.creationDate.equals(other.creationDate))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.link != null ? this.link.hashCode() : 0);
        hash = 79 * hash + (this.creationDate != null ? this.creationDate.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(Sound other) {
        if (link.compareTo(other.getLink()) != 0)
            return link.compareTo(other.getLink());
        return creationDate.compareTo(other.getCreationDate());
    }

    @Override
    public String toString() {
        return "[Sound: link=" + getLink() + ", creationDate=" + getCreationDate()+"]";
    }
}
