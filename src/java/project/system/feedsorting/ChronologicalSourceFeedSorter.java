package project.system.feedsorting;

import java.util.ArrayList;
import java.util.List;
import project.system.Sound;
import project.system.User;

/**
 * Is a feed sorter that sorts according to the chronological order of the sound
 * source addition to the source list.
 */
public class ChronologicalSourceFeedSorter implements FeedSorter {
    /**
     * Sort the user feed according to chronological order of the sound sources.
     * 
     * This function will return the sound feed sorted in such a way that
     * sounds from sources that were added first to the source list will
     * appear first, and sounds from the same source will respect the order
     * on the source post list.
     * 
     * @param user The user whose feed will be sorted and returned
     * @return the user's chronologically-source-sorted feed
     */
    @Override
    public List<Sound> sortFeed(User user) {
        List<Sound> result = new ArrayList<Sound>();

        for (int i = 0; i < user.getSourceViews().size(); i++) {
            User source = user.getSourceViews().get(i).getSource();
            result.addAll(source.getPostlist());
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
        return "chronologicalSource";
    }
}
