package project.system;

import project.exceptions.InvalidLinkException;

public class Link implements Comparable<Link> {
    private String path;
    
    public Link(String path) {
        setPath(path);
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        if (path == null || path.isEmpty())
            throw new InvalidLinkException();
        this.path = path;
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

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

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public int compareTo(Link other) {
        return path.compareTo(other.getPath());
    }
}
