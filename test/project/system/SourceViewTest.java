/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the SourceView class.
 */
public class SourceViewTest {
    private User user;
    private SourceView sourceView;
    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        user = new User("a", "a", "a", "a");
        sourceView = new SourceView(user);
    }
    
    /**
     * Test of getSource method, of class SourceView.
     */
    @Test
    public void testGetSource() {
        assertEquals(user, sourceView.getSource());
    }

    /**
     * Test of favoriteCount-related methods.
     */
    @Test
    public void testFavoriteCount() {
        assertEquals(0, sourceView.getFavoriteCount());
        sourceView.incrementFavoriteCount();
        sourceView.incrementFavoriteCount();
        assertEquals(2, sourceView.getFavoriteCount());
        sourceView.setFavoriteCount(15);
        assertEquals(15, sourceView.getFavoriteCount());
        sourceView.incrementFavoriteCount();
        assertEquals(16, sourceView.getFavoriteCount());
    }
}
