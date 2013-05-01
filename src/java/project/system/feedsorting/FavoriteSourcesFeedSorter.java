package project.system.feedsorting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Project;
import project.system.Sound;
import project.system.User;
import project.system.statistics.Stats;

/**
 * Sorts the user main feed priorizing sounds from the sources which the user
 * favorites most.
 */
public class FavoriteSourcesFeedSorter implements FeedSorter, Serializable {

    /**
     * Creates a feed from given sources, and sorts priorizing sounds of sources
     * the user likes most.
     *
     * Sounds from the same source will be grouped together in the same order as
     * they are in the source's post list. The sources whose number of musics
     * favorited by this user is bigger will be placed on the front of the list,
     * if two sources are equal in this sense, they will keep their original
     * relative ordering.
     *
     * @param user User whose feed will be sorted and returned
     * @param sources the sources we'll use to build the feed
     * @return user's sorted feed
     */
    @Override
    public List<Sound> sortFeed(final User user, List<User> sources) {
        sources = new ArrayList<User>(sources);
        Collections.reverse(sources);
        
        Comparator<User> cmp = new Comparator<User>() {
            @Override
            public int compare(User s1, User s2) {
                Stats stats = Project.getInstance().getStats();
                return stats.getFavoritesInSourceCount(user, s2)
                        - stats.getFavoritesInSourceCount(user, s1);
            }
        };

        Collections.sort(sources, cmp); // Stable sort
        List<Sound> result = new ArrayList<Sound>();
        for (User source : sources) {
            result.addAll(new ArrayList<Sound>(source.getPostlist()));
        }
        return result;
    }

    /**
     * Returns the name of the rule used by this sorter.
     *
     * @return the rule name
     */
    @Override
    public String getRuleName() {
        return "favoriteSources";
    }
}
