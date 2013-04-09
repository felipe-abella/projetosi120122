package project.system.recommender;

import java.util.List;
import project.system.User;

/**
 * Potential friend score comparer that works by summing the scores given by
 * others PFSCs.
 */
public class ScoreSumPFSC extends PotentialFriendScoreComparator {

    private List<PotentialFriendScoreComparator> comparers;

    /**
     * Constructs a new ScoreSumPFSC.
     *
     * @param pfc the comparator whose ties we will break
     * @param comparers the list of score comparators whose scores will be
     * summed
     */
    public ScoreSumPFSC(PotentialFriendComparator pfc, List<PotentialFriendScoreComparator> comparers) {
        super(pfc);
        this.comparers = comparers;
    }

    /**
     * Constructs a new ScoreSumPFSC.
     *
     * @param friendlyUser the user whose potential friends are being compared
     * @param comparers the list of score comparators whose scores will be
     * summed
     */
    public ScoreSumPFSC(User friendlyUser, List<PotentialFriendScoreComparator> comparers) {
        super(friendlyUser);
        this.comparers = comparers;
    }

    /**
     * Returns the score of a given user.
     * 
     * The score will be the sum of the scores given by each PFSCs in the list.
     * 
     * @param user the potential user
     * @return the score
     */
    @Override
    public int getScore(User user) {
        int result = 0;

        for (PotentialFriendScoreComparator pfsc : comparers) {
            result += pfsc.getScore(user);
        }

        return result;
    }
}
