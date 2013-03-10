package project.system;

/**
 * Represents how a User "sees" a Source.
 *
 * An SourceView must have all information related to a Source that's only
 * important for one specific User, for example, how many Sounds of this source
 * one user have as a favorite one.
 */
public class SourceView {

    private User source;
    private int favoriteCount;

    /**
     * Creates a new SourceView.
     *
     * @param source The source
     */
    public SourceView(User source) {
        this.source = source;
        favoriteCount = 0;
    }

    /**
     * Returns the source
     *
     * @return the source
     */
    public User getSource() {
        return source;
    }

    /**
     * Returns how many sounds of this source the user have as a favorite one.
     *
     * @return the favorite count
     */
    public int getFavoriteCount() {
        return favoriteCount;
    }

    /**
     * Increases the favorite count by one (see #getFavoriteCount for a
     * definition).
     */
    public void incrementFavoriteCount() {
        favoriteCount++;
    }

    /**
     * Sets the favorite count (see #getFavoriteCount for a definition).
     *
     * @param favoriteCount the new favorite count
     */
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
