package project.system.feedsorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import project.system.Sound;
import project.system.SourceView;
import project.system.User;

public class FavoriteSourcesFeedSorter implements FeedSorter {
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
        for (SourceView sview: sviews) {
            List<Sound> bla = new ArrayList<Sound>(sview.getSource().getPostlist());
            result.addAll(bla);
        }
        
        return result;
    }
    
    @Override
    public String getRuleName() {
        return "favoriteSources";
    }
}
