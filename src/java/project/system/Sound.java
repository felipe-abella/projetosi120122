package project.system;

import project.exceptions.InvalidCreationDateException;
import project.exceptions.InvalidDateException;

public class Sound implements Comparable<Sound> {
    private Link link;
    private SimpleDate creationDate;
    private User author;
    private int favoriteCount;
    
    public Sound(Link link, SimpleDate creationDate, User author) {
        this.link = link;
        this.creationDate = creationDate;
        this.author = author;
        favoriteCount = 0;
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

    public int getFavoriteCount() {
        return favoriteCount;
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

    public void incrementFavoriteCount() {
        favoriteCount++;
    }
    
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.link != null ? this.link.hashCode() : 0);
        hash = 29 * hash + (this.creationDate != null ? this.creationDate.hashCode() : 0);
        hash = 29 * hash + (this.author != null ? this.author.hashCode() : 0);
        return hash;
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
        if (this.author != other.author && (this.author == null || !this.author.equals(other.author))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Sound other) {
        if (link.compareTo(other.getLink()) != 0)
            return link.compareTo(other.getLink());
        if (creationDate.compareTo(other.getCreationDate()) != 0)
            return creationDate.compareTo(other.getCreationDate());
        return author.compareTo(other.getAuthor());
    }

    @Override
    public String toString() {
        return "[S: " + (link.hashCode()+author.hashCode()) + " CD: " + getCreationDate() + "]";
        //return "[Sound: link=" + getLink() + ", creationDate=" + getCreationDate()+", author=" + author + "]";
    }
}
