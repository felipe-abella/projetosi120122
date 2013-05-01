package project.system.feedfiltering;

import java.io.Serializable;
import project.system.Feed;
import project.system.Sound;

/**
 * Represents an open feed filter which let all posts pass.
 */
public class OpenFeedFilter extends FeedFilter implements Serializable {

    private Feed feed;

    /**
     * Constructs a new open feed filter.
     *
     * @param feed the feed
     */
    public OpenFeedFilter(Feed feed) {
        this.feed = feed;
    }

    /**
     * Returns the feed.
     *
     * @return the feed
     */
    @Override
    public Feed getFeed() {
        return feed;
    }

    /**
     * Returns whether this filter will block a given sound.
     *
     * @param sound the sound
     * @return if it will be blocked
     */
    @Override
    public boolean willBlock(Sound sound) {
        return false;
    }
}
