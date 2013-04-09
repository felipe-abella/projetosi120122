package project.system.recommender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import project.system.Project;
import project.system.User;

/**
 * Class responsible for implementing functions related to friend recommending.
 */
public class FriendRecommender {

    private static List<User> getCandidates(User friendlyUser, List<User> excludedList) {
        List<User> candidates = new ArrayList<User>(Project.getInstance().getModel().getUsers());

        candidates.remove(friendlyUser);
        if (excludedList != null) {
            candidates.removeAll(excludedList);
        }

        Iterator<User> it = candidates.iterator();

        while (it.hasNext()) {
            User user = it.next();

            if (user.getSources().isEmpty() && user.getFavoriteList().isEmpty()) {
                it.remove();
            }
        }

        return candidates;
    }

    private static List<User> discardZeroScores(List<User> candidates, PotentialFriendScoreComparator cmp) {
        Iterator<User> it = candidates.iterator();

        while (it.hasNext()) {
            User user = it.next();

            if (cmp.getScore(user) == 0) {
                it.remove();
            }
        }

        return candidates;
    }
    
    /**
     * Returns friend recommendations for the friendlyUser.
     *
     * @param friendlyUser the friendly user
     * @param excludedList the list of friend that shouldn't be suggested
     * @return a list of the recommended friends
     */
    public static List<User> getFriendRecommendations(User friendlyUser, List<User> excludedList) {
        List<User> candidates = getCandidates(friendlyUser, excludedList);
        PotentialFriendComparator cmp;

        if (friendlyUser.getSources().isEmpty() && friendlyUser.getFavoriteList().isEmpty()) {
            PotentialFriendScoreComparator cmp1 = new PopularSourcePFSC(friendlyUser);
            PotentialFriendComparator cmp2 = new LexicographicalPFC(cmp1);

            discardZeroScores(candidates, cmp1);

            cmp = cmp2;
        } else {
            PotentialFriendScoreComparator cmp1a = new CommonFavoritesPFSC(friendlyUser);
            PotentialFriendScoreComparator cmp1b = new CommonSourcesPFSC(friendlyUser);
            PotentialFriendScoreComparator cmp1 = new ScoreSumPFSC(friendlyUser, Arrays.asList(cmp1a, cmp1b));
            PotentialFriendComparator cmp2 = new FavoritedPostsPFSC(cmp1);
            PotentialFriendComparator cmp3 = new LexicographicalPFC(cmp2);

            discardZeroScores(candidates, cmp1);

            cmp = cmp3;
        }

        Collections.sort(candidates, cmp);

        return candidates;
    }
}
