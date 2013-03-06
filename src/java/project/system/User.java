package project.system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import project.exceptions.InvalidEmailException;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidNameException;

public class User implements Comparable<User> {
    private String login, password, name, email;
    private List<Sound> postlist;
    private List<User> sources;
    private SortedSet<User> followers;
    
    public User(String login, String password, String name, String email) {
        setLogin(login);
        setPassword(password);
        setName(name);
        setEmail(email);
        
        postlist = new LinkedList<Sound>();
        sources = new ArrayList<User>();
        followers = new TreeSet<User>();
    }

    public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public List<User> getSources() {
        return sources;
    }
    
    public SortedSet<User> getFollowers() {
        return followers;
    }
    
    public List<Sound> getPostlist() {
        return postlist;
    }
    
    public void setLogin(String login) {
        if (login == null || login.isEmpty())
            throw new InvalidLoginException();
        this.login = login;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new InvalidNameException();
        this.name = name;
    }
    
    public void setEmail(String email) {
        if (email == null || email.isEmpty())
            throw new InvalidEmailException();
        this.email = email;
    }
    
    public void setPostlist(List<Sound> postlist) {
        this.postlist = postlist;
    }
    
    public void setSources(List<User> sources) {
        this.sources = sources;
    }
    
    public void setFollowers(SortedSet<User> followers) {
        this.followers = followers;
    }
    
    public int countSources() {
        return sources.size();
    }
    
    public int countFollowers() {
        return followers.size();
    }
    
    public void post(Sound sound) {
        postlist.add(0, sound);
    }
    
    public List<Sound> getSoundFeed() {
        List<Sound> result = new ArrayList<Sound>();
        for (int i = sources.size()-1; i >= 0; i--) {
            User source = sources.get(i);
            result.addAll(source.getPostlist());
        }
        return result;
    }

    @Override
    public int compareTo(User other) {
        return login.compareTo(other.getLogin());
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

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

    @Override
    public String toString() {
        return "[User, login=" + getLogin() + ", name=" + getName() + "]";
    }
}
