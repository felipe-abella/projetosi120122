package project.system.recommender;

import project.system.User;

/**
 * Represents a potential friend comparator that works by calculating a score
 * number for each potential friend, priorizing higher scores.
 */
public abstract class PotentialFriendScoreComparator
        implements PotentialFriendComparator {

    private PotentialFriendComparator decoratedPFC;

    /**
     * Initiates a PotentialFriendScoreComparator.
     *
     * @param friendlyUser the user whose potential friends are being compared
     */
    public PotentialFriendScoreComparator(User friendlyUser) {
        this(new IndifferentPFC(friendlyUser));
    }

    /**
     * Initiates a PotentialFriendScoreComparator.
     *
     * @param pfc the comparator whose ties we will break
     */
    public PotentialFriendScoreComparator(PotentialFriendComparator pfc) {
        decoratedPFC = pfc;
    }

    /**
     * Returns the score given to a certain potential friend.
     *
     * @param user the potential friend
     * @return the score
     */
    public abstract int getScore(User user);

    /**
     * Compares two potential friends using the score.
     *
     * The potential friend that have higher score will come first.
     *
     * @param pf1 Potential friend 1
     * @param pf2 Potential friend 2
     * @return < 0, = 0 or > 0, depending on how they compare
     */
    @Override
    public int compare(User pf1, User pf2) {
        int result = decoratedPFC.compare(pf1, pf2);

        if (result == 0) {
            result = getScore(pf2) - getScore(pf1);
        }

        return result;
    }

    /**
     * Returns the user whose potential friends are being compared.
     *
     * @return the friendly user
     */
    @Override
    public User getFriendlyUser() {
        return decoratedPFC.getFriendlyUser();
    }
}
