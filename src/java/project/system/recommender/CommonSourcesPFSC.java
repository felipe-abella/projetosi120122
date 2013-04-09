package project.system.recommender;

import project.system.Project;
import project.system.User;

/**
 * This class implements a potential friend score comparator which takes into
 * account the amount of sources the friendly user and each potential friend
 * have in common.
 */
public class CommonSourcesPFSC extends PotentialFriendScoreComparator {

    /**
     * Creates a new CommonSourcesPFSC.
     *
     * @param pfc The comparator whose ties we will break
     */
    public CommonSourcesPFSC(PotentialFriendComparator pfc) {
        super(pfc);
    }

    /**
     * Creates a new CommonSourcesPFSC.
     *
     * @param friendlyUser the friendly user
     */
    public CommonSourcesPFSC(User friendlyUser) {
        super(friendlyUser);
    }

    /**
     * Returns the amount of common sources between the friendly user and user.
     *
     * @param user the potential friend
     * @return the amount of common sources
     */
    @Override
    public int getScore(User user) {
        return Project.getInstance().getStats().getCommonSourcesCount(getFriendlyUser(), user);
    }
}
