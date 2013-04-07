package project.system.feedsorting;

import java.util.List;
import project.system.Sound;
import project.system.User;

/**
 * A FeedSorter is able to sort a user's main feed according to some criterion.
 */
public interface FeedSorter {

    /**
     * Creates a feed from given sources, and sorts according to the
     * rule given by getRuleName().
     *
     * @param user the user's whose feed will be sorted and returned
     * @param sources the sources we'll use to build the feed
     * @return user's sorted feed
     */
    public List<Sound> sortFeed(User user, List<User> sources);

    /**
     * Returns the name of the rule used by this sorter.
     *
     * @return the rule name
     */
    public String getRuleName();
}
