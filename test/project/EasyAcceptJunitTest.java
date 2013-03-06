package project;

import java.util.GregorianCalendar;
import project.EasyAcceptProjectTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EasyAcceptJunitTest {
    public EasyAcceptJunitTest() {
    }

    @Test
    public void testEasyAccept() {
        EasyAcceptProjectTest eapTest = new EasyAcceptProjectTest();
        assertTrue(eapTest.easyAcceptSuccess());
    }
}
