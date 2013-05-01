package project.system.feedfiltering;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import project.system.Feed;
import project.system.Sound;

/**
 * A FeedFilter is able to choose a subset of posts from a feed.
 */
public abstract class FeedFilter implements Iterable<Sound>, Serializable {
    /**
     * Returns the feed that will be filtered.
     * 
     * @return the feed
     */
    public abstract Feed getFeed();
    
    /**
     * Returns whether this filter will block a given sound.
     * 
     * @param sound the sound
     * @return if it will be blocked
     */
    public abstract boolean willBlock(Sound sound);

    /**
     * Constructs an iterator for the filtered list.
     * 
     * @return the iterator
     */
    @Override
    public Iterator<Sound> iterator() {
        return new Iterator<Sound>() {
            private Sound buffer = null;
            private Iterator<Sound> feedIt = getFeed().getPosts().iterator();

            @Override
            public boolean hasNext() {
                while (buffer == null && feedIt.hasNext()) {
                    Sound nextSound = feedIt.next();
                    if (!willBlock(nextSound))
                        buffer = nextSound;
                }
                return buffer != null;
            }

            @Override
            public Sound next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                
                Sound result = buffer;
                buffer = null;
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
