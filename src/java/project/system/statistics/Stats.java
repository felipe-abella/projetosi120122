package project.system.statistics;

import project.system.Project;
import project.system.Sound;
import project.system.User;

/**
 * Class responsible for calculating useful statistics about the Project.
 */
public class Stats {

    private Project project;

    /**
     * Constructs the Stats for a given project.
     *
     * @param project the project
     */
    public Stats(Project project) {
        this.project = project;
    }

    /**
     * Count how many favorites of user are in source post list.
     *
     * @param user the user
     * @param source the source
     * @return how many favorites
     */
    public int getFavoritesInSourceCount(User user, User source) {
        int result = 0;

        for (Sound favorite : user.getFavoriteList()) {
            if (source.getPostlist().contains(favorite)) {
                result++;
            }
        }

        return result;
    }

    /**
     * Return the amount of times this sound was favorited by a user.
     *
     * @param sound the sound
     * @return the amount of times
     */
    public int getSoundFavoriteCount(Sound sound) {
        int result = 0;

        for (User user : project.getModel().getUsers()) {
            if (user.getFavoriteList().contains(sound)) {
                result++;
            }
        }

        return result;
    }

    /**
     * Returns how many favorite sounds two users have in common.
     *
     * @param user1 the first user
     * @param user2 the second user
     * @return the amount of common favorite sounds
     */
    public int getCommonFavoritesCount(User user1, User user2) {
        int result = 0;

        for (Sound sound : user1.getFavoriteList()) {
            if (user2.getFavoriteList().contains(sound)) {
                result++;
            }
        }

        return result;
    }

    /**
     * Returns how many sources two users have in common.
     *
     * @param user1 the first user
     * @param user2 the second user
     * @return the amount of common sources
     */
    public int getCommonSourcesCount(User user1, User user2) {
        int result = 0;

        for (User source : user1.getSources()) {
            if (user2.getSources().contains(source)) {
                result++;
            }
        }

        return result;
    }

    /**
     * Returns how many posts from a user were favorited by someone.
     *
     * @param user the user
     * @return how many posts
     */
    public int getFavoritedPostsCount(User user) {
        int result = 0;

        for (Sound sound : user.getPostlist()) {
            if (getSoundFavoriteCount(sound) > 0) {
                result++;
            }
        }

        return result;
    }

    /**
     * Clears all statistics about the Project.
     *
     * This function should be called when the Project model is reseted.
     */
    public void clear() {
    }
}
