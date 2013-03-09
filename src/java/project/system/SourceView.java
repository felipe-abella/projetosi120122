package project.system;

public class SourceView {
    private User source;
    private int favoriteCount;

    public SourceView(User source) {
        this.source = source;
        favoriteCount = 0;
    }

    public User getSource() {
        return source;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void incrementFavoriteCount() {
        favoriteCount++;
    }
    
    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
