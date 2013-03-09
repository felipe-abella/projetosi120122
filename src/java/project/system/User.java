package project.system;

import project.system.feedsorting.FeedSorter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import project.exceptions.InvalidEmailException;
import project.exceptions.InvalidLoginException;
import project.exceptions.InvalidNameException;
import project.exceptions.InvalidSoundException;
import project.system.feedsorting.CronologicalSourceFeedSorter;

public class User implements Comparable<User> {
    private String login, password, name, email;
    private List<Sound> postlist;
    private List<SourceView> sourceViews;
    private SortedSet<User> followers;
    private List<Sound> favoriteList;
    private FeedSorter feedSorter;
    
    public User(String login, String password, String name, String email) {
        setLogin(login);
        setPassword(password);
        setName(name);
        setEmail(email);
        
        postlist = new LinkedList<Sound>();
        sourceViews = new ArrayList<SourceView>();
        followers = new TreeSet<User>();
        favoriteList = new LinkedList<Sound>();
        feedSorter = new CronologicalSourceFeedSorter();
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

    public FeedSorter getFeedSorter() {
        return feedSorter;
    }
    
    public List<User> getSources() {
        List<User> result = new ArrayList<User>();
        for (SourceView sview: sourceViews)
            result.add(sview.getSource());
        return result;
    }

    public List<SourceView> getSourceViews() {
        return sourceViews;
    }
    
    public SortedSet<User> getFollowers() {
        return followers;
    }
    
    public List<Sound> getPostlist() {
        return postlist;
    }
    
    public List<Sound> getFavoriteList() {
        return favoriteList;
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

    public void setFollowers(SortedSet<User> followers) {
        this.followers = followers;
    }

    public void setFeedSorter(FeedSorter feedSorter) {
        this.feedSorter = feedSorter;
    }
    
    public int countSources() {
        return sourceViews.size();
    }
    
    public int countFollowers() {
        return followers.size();
    }
    
    public void post(Sound sound) {
        postlist.add(0, sound);
    }
    
    public SourceView getViewOfSource(User source) {
        for (SourceView sview: sourceViews)
            if (sview.getSource().equals(source))
                return sview;
        return null;
    }
    
    public boolean addFavorite(Sound sound) {
        if (sound == null)
            throw new InvalidSoundException();
        if (!favoriteList.contains(sound)) {
            favoriteList.add(0, sound);
            sound.incrementFavoriteCount();
            SourceView sview = getViewOfSource(sound.getAuthor());
            if (sview != null)
                sview.incrementFavoriteCount();
            return true;
        }
        return false;
    }
    
    public void addSource(User source) {
        SourceView sview = new SourceView(source);

        for (Sound sound: getFavoriteList())
            if (sound.getAuthor().equals(source))
                sview.incrementFavoriteCount();
        
        sourceViews.add(sview);
    }
    
    public void addFollower(User follower) {
        followers.add(follower);
    }
    
    public List<Sound> getUnsortedSoundFeed() {
        List<Sound> result = new ArrayList<Sound>();
        for (int i = sourceViews.size()-1; i >= 0; i--) {
            User source = sourceViews.get(i).getSource();
            result.addAll(source.getPostlist());
        }
        return result;
    }
    
    public List<Sound> getSortedSoundFeed() {
        return feedSorter.sortFeed(this);
    }
    
    public List<Sound> getExtraSoundFeed() {
        List<Sound> result = new ArrayList<Sound>();
        for (int i = sourceViews.size()-1; i >= 0; i--) {
            User source = sourceViews.get(i).getSource();
            result.addAll(source.getFavoriteList());
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
