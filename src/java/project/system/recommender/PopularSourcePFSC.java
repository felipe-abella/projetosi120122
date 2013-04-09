package project.system.recommender;

import project.system.Project;
import project.system.User;

/**
 * This class implements a potential friend score comparator which takes into
 * account the amount of sounds from a potential friend's post list were
 * favorited by someone.
 */
public class PopularSourcePFSC extends PotentialFriendScoreComparator {

    /**
     * Creates a new PopularSourcePFSC.
     *
     * @param pfc The comparator whose ties we will break
     */
    public PopularSourcePFSC(PotentialFriendComparator pfc) {
        super(pfc);
    }

    /**
     * Creates a new PopularSourcePFSC.
     *
     * @param friendlyUser the friendly user
     */
    public PopularSourcePFSC(User friendlyUser) {
        super(friendlyUser);
    }

    /**
     * Returns the amount of sounds from the potential friend's post list were
     * favorited by someone.
     *
     * @param user the potential friend
     * @return the amount of sounds
     */
    @Override
    public int getScore(User user) {
        return Project.getInstance().getStats().getFavoritedPostsCount(user);
    }
}
