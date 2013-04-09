package project.system.recommender;

import project.system.Project;
import project.system.User;

/**
 * This class implements a potential friend score comparator which takes into
 * account the amount of favorites the friendly user and each potential friend
 * have in common.
 */
public class CommonFavoritesPFSC extends PotentialFriendScoreComparator {

    /**
     * Creates a new CommonFavoritesPFSC.
     *
     * @param pfc The comparator whose ties we will break
     */
    public CommonFavoritesPFSC(PotentialFriendComparator pfc) {
        super(pfc);
    }

    /**
     * Creates a new CommonFavoritesPFSC.
     *
     * @param friendlyUser the friendly user
     */
    public CommonFavoritesPFSC(User friendlyUser) {
        super(friendlyUser);
    }

    /**
     * Returns the amount of common favorites between the friendly user and
     * user.
     *
     * @param user the potential friend
     * @return the amount of common favorites
     */
    @Override
    public int getScore(User user) {
        return Project.getInstance().getStats().getCommonFavoritesCount(getFriendlyUser(), user);
    }
}
