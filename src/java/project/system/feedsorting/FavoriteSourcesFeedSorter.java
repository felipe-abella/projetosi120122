package project.system.feedsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Sound;
import project.system.SourceView;
import project.system.User;

/**
 * Sorts the user main feed priorizing sounds from the sources which the user
 * favorites most.
 */
public class FavoriteSourcesFeedSorter implements FeedSorter {

    /**
     * Sorts user's main feed priorizing sounds of sources the user likes most.
     *
     * Sounds from the same source will be grouped together in the same order as
     * they are in the source's post list. The sources whose number of musics
     * favorited by this user is bigger will be placed on the front of the list,
     * if two sources are equal in this sense, they will keep their original
     * relative ordering.
     *
     * @param user User whose feed will be sorted and returned
     * @return user's sorted feed
     */
    @Override
    public List<Sound> sortFeed(User user) {
        List<SourceView> sviews = new ArrayList<SourceView>(user.getSourceViews());
        Collections.reverse(sviews);

        Comparator<SourceView> cmp = new Comparator<SourceView>() {
            @Override
            public int compare(SourceView sv1, SourceView sv2) {
                return sv2.getFavoriteCount() - sv1.getFavoriteCount();
            }
        };
        Collections.sort(sviews, cmp); // Stable sort

        List<Sound> result = new ArrayList<Sound>();
        for (SourceView sview : sviews) {
            List<Sound> bla = new ArrayList<Sound>(sview.getSource().getPostlist());
            result.addAll(bla);
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
