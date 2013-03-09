package project.system.feedsorting;

import java.util.List;
import project.system.Sound;
import project.system.User;

public interface FeedSorter {
        public List<Sound> sortFeed(User user);
        public String getRuleName();
}
