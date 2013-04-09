package project.system.recommender;

import project.system.Project;
import project.system.User;

/**
 * This class implements a potential friend score comparator which takes into
 * account the amount of times the friendly user favorited a post from each of
 * the potential friends.
 */
public class FavoritedPostsPFSC extends PotentialFriendScoreComparator {

    /**
     * Creates a new FavoritedPostsPFSC.
     *
     * @param pfc The comparator whose ties we will break
     */
    public FavoritedPostsPFSC(PotentialFriendComparator pfc) {
        super(pfc);
    }

    /**
     * Creates a new FavoritedPostsPFSC.
     *
     * @param friendlyUser the friendly user
     */
    public FavoritedPostsPFSC(User friendlyUser) {
        super(friendlyUser);
    }

    /**
     * Returns the amount of times the friendly user favorited a user's post.
     *
     * @param user the potential friend
     * @return the score
     */
    @Override
    public int getScore(User user) {
        return Project.getInstance().getStats().getFavoritesInSourceCount(getFriendlyUser(), user);
    }
}
