package project.system.recommender;

import java.util.Comparator;
import project.system.User;

/**
 * Represents a class capable of comparing two potential friends for a user
 * (called "friendly user"), returning the most promising potential friend (if
 * any).
 */
public interface PotentialFriendComparator extends Comparator<User> {

    /**
     * Returns the user whose potential friends are being compared.
     *
     * @return the friendly user
     */
    public User getFriendlyUser();
}
