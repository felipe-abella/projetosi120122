package project.system.recommender;

import project.system.User;

/**
 * This class implements a indifferent potential friend comparator.
 *
 * The comparator will always return that two potential friends are equally
 * promising. This class allows some code simplification, by letting you use
 * polymorphism in places where potential friends shouldn't be compared. See
 * PotentialFriendScoreComparator's code for an example.
 */
public class IndifferentPFC implements PotentialFriendComparator {

    private User friendlyUser;

    /**
     * Creates a new IndifferentPFC.
     *
     * @param friendlyUser the friendly user
     */
    public IndifferentPFC(User friendlyUser) {
        this.friendlyUser = friendlyUser;
    }

    /**
     * Returns the user whose potential friends are being compared.
     *
     * @return the friendly user
     */
    @Override
    public User getFriendlyUser() {
        return friendlyUser;
    }

    /**
     * Compares two potential friends indifferently.
     *
     * It will always return that the two potential friends are equally
     * promising.
     *
     * @param pf1 Potential friend 1
     * @param pf2 Potential friend 2
     * @return 0
     */
    @Override
    public int compare(User pf1, User pf2) {
        return 0;
    }
}
