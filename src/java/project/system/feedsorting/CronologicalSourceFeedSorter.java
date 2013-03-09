package project.system.feedsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Sound;
import project.system.User;

public class CronologicalSourceFeedSorter implements FeedSorter {
    @Override
    public List<Sound> sortFeed(User user) {
        List<Sound> result = new ArrayList<Sound>();

        for (int i = 0; i < user.getSourceViews().size(); i++) {
            User source = user.getSourceViews().get(i).getSource();
            result.addAll(source.getPostlist());
        }
        
        return result;
    }

    @Override
    public String getRuleName() {
        return "cronologicalSource";
    }
}
