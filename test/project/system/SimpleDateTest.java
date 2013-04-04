package project.system;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import project.exceptions.InvalidDateException;

/**
 * Tests SimpleDate.
 */
public class SimpleDateTest {

    private SimpleDate date1, date2;

    /**
     * Set up the tests.
     */
    @Before
    public void setUp() {
        date1 = new SimpleDate(2013, 3, 11);
        date2 = new SimpleDate(2013, 3, 15);
    }

    /**
     * Test of getYear method, of class SimpleDate.
     */
    @Test
    public void testGetYear() {
        assertEquals(2013, date1.getYear());
    }

    /**
     * Test of getMonth method, of class SimpleDate.
     */
    @Test
    public void testGetMonth() {
        assertEquals(3, date1.getMonth());
    }

    /**
     * Test of getDay method, of class SimpleDate.
     */
    @Test
    public void testGetDay() {
        assertEquals(11, date1.getDay());
    }

    /**
     * Test of setSimpleDate method, of class SimpleDate.
     */
    @Test
    public void testSetSimpleDate_3args() {
        date2.setSimpleDate(2014, 1, 2);
        assertEquals(2, date2.getDay());
        assertEquals(1, date2.getMonth());
        assertEquals(2014, date2.getYear());
    }

    /**
     * Test of setSimpleDate method, of class SimpleDate.
     */
    @Test
    public void testSetSimpleDate_String() {
        date2.setSimpleDate("2/1/2014");
        assertEquals(2, date2.getDay());
        assertEquals(1, date2.getMonth());
        assertEquals(2014, date2.getYear());
    }

    /**
     * Test exception from setSimpleDate method, of class SimpleDate.
     */
    @Test(expected = InvalidDateException.class)
    public void testSetSimpleDate_String_Exception() {
        date2.setSimpleDate("2/1/201A");
    }

    /**
     * Test of isLeapYear method, of class SimpleDate.
     */
    @Test
    public void testIsLeapYear() {
        assertTrue(SimpleDate.isLeapYear(2004));
        assertTrue(SimpleDate.isLeapYear(2000));
        assertFalse(SimpleDate.isLeapYear(2100));
        assertFalse(SimpleDate.isLeapYear(2001));
        assertFalse(SimpleDate.isLeapYear(2002));
        assertFalse(SimpleDate.isLeapYear(2003));
    }

    /**
     * Test of isDateOld method, of class SimpleDate.
     */
    @Test
    public void testIsDateOld() {
        assertTrue(new SimpleDate(1930, 1, 1).isDateOld());
        assertTrue(new SimpleDate(1950, 1, 1).isDateOld());
        assertFalse(new SimpleDate(98761, 1, 1).isDateOld());
    }

    /**
     * Test of isDateValid method, of class SimpleDate.
     */
    @Test
    public void testIsDateValid() {
        assertTrue(SimpleDate.isDateValid(2013, 3, 11));
        assertFalse(SimpleDate.isDateValid(2013, 3, 32));
        assertFalse(SimpleDate.isDateValid(2013, 2, 29));
        assertTrue(SimpleDate.isDateValid(2000, 2, 29));
    }

    /**
     * Test of equals method, of class SimpleDate.
     */
    @Test
    public void testEquals() {
        assertFalse(date1.equals(date2));
        assertTrue(date1.equals(date1));
        assertTrue(date1.equals(new SimpleDate(2013, 3, 11)));
    }

    /**
     * Test of compareTo method, of class SimpleDate.
     */
    @Test
    public void testCompareTo() {
        assertTrue(date1.compareTo(date2) < 0);
        assertTrue(date2.compareTo(date1) > 0);
        assertTrue(date1.compareTo(date1) == 0);
        assertTrue(date2.compareTo(date2) == 0);
    }
}
