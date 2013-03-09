package project.system.feedsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Sound;
import project.system.User;

public class MostFavoritedFeedSorter implements FeedSorter {

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

    @Override
    public String getRuleName() {
        return "mostFavorited";
    }
}
