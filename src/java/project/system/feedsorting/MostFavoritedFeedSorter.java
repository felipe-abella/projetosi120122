package project.system.feedsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Project;
import project.system.Sound;
import project.system.User;
import project.system.statistics.Stats;

/**
 * Is a feed sorter that priorizes the sounds that have the bigger amount of
 * users which included it in the favorite list.
 */
public class MostFavoritedFeedSorter implements FeedSorter {

    /**
     * Creates a feed from given sources, and sorts according to the amount of
     * favorite each sound received.
     *
     * If two sounds have the same amount of favorites, they will keep their
     * original relative ordering.
     *
     * @param user The user whose feed will be sorted and returned
     * @param sources the sources we'll use to build the feed
     * @return the sorted feed
     */
    @Override
    public List<Sound> sortFeed(User user, List<User> sources) {
        List<Sound> result = new ArrayList<Sound>();

        Comparator<Sound> cronCmp = new Comparator<Sound>() {
            @Override
            public int compare(Sound s1, Sound s2) {
                Stats stats = Project.getInstance().getStats();
                return stats.getSoundFavoriteCount(s2) - stats.getSoundFavoriteCount(s1);
            }
        };

        for (int i = 0; i < sources.size(); i++) {
            User source = sources.get(i);
            result.addAll(source.getPostlist());
        }

        Collections.sort(result, cronCmp); // Stable sort

        return result;
    }

    /**
     * Returns the name of the rule used by this sorter.
     *
     * @return the rule name
     */
    @Override
    public String getRuleName() {
        return "mostFavorited";
    }
}
