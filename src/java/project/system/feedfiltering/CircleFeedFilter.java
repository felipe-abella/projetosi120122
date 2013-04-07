package project.system.feedfiltering;

import project.system.Circle;
import project.system.Feed;
import project.system.Sound;

/**
 * Filters a feed selecting only posts from users of a specified Circle.
 */
public class CircleFeedFilter extends FeedFilter {

    private FeedFilter filter;
    private Circle circle;

    /**
     * Constructs the circle feed filter.
     *
     * @param filter The decorated filter
     * @param circle The chosen circle
     */
    public CircleFeedFilter(FeedFilter filter, Circle circle) {
        this.filter = filter;
        this.circle = circle;
    }

    /**
     * Returns the feed that will be filtered.
     *
     * @return the feed
     */
    @Override
    public Feed getFeed() {
        return filter.getFeed();
    }

    /**
     * Returns whether this filter will block a given sound.
     *
     * @param sound the sound
     * @return if it will be blocked
     */
    @Override
    public boolean willBlock(Sound sound) {
        return !circle.containsUser(sound.getAuthor()) || filter.willBlock(sound);
    }
}
