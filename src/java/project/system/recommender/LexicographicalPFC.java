package project.system.recommender;

import project.system.User;

public class LexicographicalPFC implements PotentialFriendComparator {

    private PotentialFriendComparator decoratedPFC;

    /**
     * Constructs a new LexicographicalPFC.
     *
     * @param friendlyUser the user whose potential friends are being compared
     */
    public LexicographicalPFC(User friendlyUser) {
        this(new IndifferentPFC(friendlyUser));
    }

    /**
     * Constructs a new LexicographicalPFC.
     *
     * @param pfc the comparator whose ties we will break
     */
    public LexicographicalPFC(PotentialFriendComparator pfc) {
        decoratedPFC = pfc;
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

    /**
     * Compare two potential friends, breaking ties with lexicographical name
     * order.
     *
     * @param pf1 Potential friend 1
     * @param pf2 Potential friend 2
     * @return < 0, = 0 or > 0, depending on how they compare
     */
    @Override
    public int compare(User pf1, User pf2) {
        int result = decoratedPFC.compare(pf1, pf2);

        if (result == 0) {
            result = pf1.getName().compareTo(pf2.getName());
        }

        return result;
    }
}
