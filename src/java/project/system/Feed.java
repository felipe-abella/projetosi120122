package project.system;

/**
 * Represents a user feed.
 */
public class Feed {
    private User owner;
    private Iterable<Sound> posts;

    /**
     * Constructs a new feed.
     * 
     * @param owner Owner of the feed.
     * @param posts Posts of the feed.
     */
    public Feed(User owner, Iterable<Sound> posts) {
        this.owner = owner;
        this.posts = posts;
    }

    /**
     * Return the owner of the Feed.
     * 
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Return the posts of the Feed.
     * 
     * @return the posts
     */
    public Iterable<Sound> getPosts() {
        return posts;
    }
}
