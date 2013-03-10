package project.system.feedsorting;

import java.util.List;
import project.system.Sound;
import project.system.User;

/**
 * A FeedSorter is able to sort a user's main feed according to some criterion.
 */
public interface FeedSorter {

    /**
     * Sorts the user's main feed according to the rule given by getRuleName()
     * @param user the user's whose feed will be sorted and returned
     * @return user's sorted feed
     */
    public List<Sound> sortFeed(User user);

    /**
     * Returns the name of the rule used by this sorter.
     *
     * @return the rule name
     */
    public String getRuleName();
}
