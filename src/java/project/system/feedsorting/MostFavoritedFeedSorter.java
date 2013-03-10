package project.system.feedsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Sound;
import project.system.User;

/**
 * Is a feed sorter that priorizes the sounds that have the bigger amount of
 * users which included it in the favorite list.
 */
public class MostFavoritedFeedSorter implements FeedSorter {

    /**
     * Sort the user feed according to the amount of favorite each sound
     * received.
     *
     * If two sounds have the same amount of favorites, they will keep their
     * original relative ordering.
     *
     * @param user The user whose feed will be sorted and returned
     * @return the sorted feed
     */
    @Override
    public List<Sound> sortFeed(User user) {
        List<Sound> result = new ArrayList<Sound>();

        Comparator<Sound> cronCmp = new Comparator<Sound>() {
            @Override
            public int compare(Sound s1, Sound s2) {
                return s2.getFavoriteCount() - s1.getFavoriteCount();
            }
        };

        for (int i = 0; i < user.getSourceViews().size(); i++) {
            User source = user.getSourceViews().get(i).getSource();
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
