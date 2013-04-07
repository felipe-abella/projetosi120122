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
            if (source.getPostlist().contains(favorite))
                result ++;
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
        
        for (User user : project.getModel().getUsers())
            if (user.getFavoriteList().contains(sound))
                result ++;
        
        return result;
    }
}
