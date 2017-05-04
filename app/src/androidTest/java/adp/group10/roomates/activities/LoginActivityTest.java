package adp.group10.roomates.activities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import adp.group10.roomates.businesslogic.LoginManager;

/**
 * Created by Rimsh on 04/05/17.
 */
public class LoginActivityTest {

    LoginManager lgmgr;
    @Before
    public void setUp() throws Exception {
        lgmgr = new LoginManager();
    }

    @Test
    public void checkEmail()
    {
        assertEquals(lgmgr.isValidEmail("examplegmail.com"),false);
        assertEquals(lgmgr.isValidEmail(""),false);
        assertEquals(lgmgr.isValidEmail("examplegmailcom"),false);
        assertEquals(lgmgr.isValidEmail("@.egmailcom"),false);
        assertEquals(lgmgr.isValidEmail("example@.egmailcom"),false);
        assertEquals(lgmgr.isValidEmail(".egmailcom"),false);
        assertEquals(lgmgr.isValidEmail("@egmailcom"),false);
        assertEquals(lgmgr.isValidEmail("exampl@egmailcom"),true);
        assertEquals(lgmgr.isValidEmail("example@gmail.com"),true);
    }
    @Test
    public void checkPassword()
    {
        assertEquals(lgmgr.isValidPassword("example"),false);  // just small letters characters
        assertEquals(lgmgr.isValidPassword("564321786"),false); // just numbers
        assertEquals(lgmgr.isValidPassword("example123"),false); //   mix small letters and numbers
        assertEquals(lgmgr.isValidPassword("examp123le"),false); //   mix small letters and numbers
        assertEquals(lgmgr.isValidPassword("123example"),false); //   mix small letters and numbers
        assertEquals(lgmgr.isValidPassword("example 123"),false); // containing space
        assertEquals(lgmgr.isValidPassword(" 123example"),false); // containing space
        assertEquals(lgmgr.isValidPassword("123example "),false); // containing space
        assertEquals(lgmgr.isValidPassword(" example123"),false); // containing space
        assertEquals(lgmgr.isValidPassword("Example 123"),false); // containing space
        assertEquals(lgmgr.isValidPassword("EXAMPLE 123"),false); // containing space



        assertEquals(lgmgr.isValidPassword("Example123"),false); //   mix small letters and capital letters and numbers
        assertEquals(lgmgr.isValidPassword("123Example"),false); //   mix small letters and capital letters and numbers
        assertEquals(lgmgr.isValidPassword("Exa123mple"),false); //   mix small letters and capital letters and numbers

        assertEquals(lgmgr.isValidPassword("EXAMPLE"),false);    //   only capital letters and numbers
        assertEquals(lgmgr.isValidPassword("EXAMPLE123"),false); //   mix capital letters and numbers
        assertEquals(lgmgr.isValidPassword("123EXAMPLE"),false); //   mix capital letters and numbers


        assertEquals(lgmgr.isValidPassword("EXAMple"),false);    //   mix capital letters and small letters
        assertEquals(lgmgr.isValidPassword("exampLE"),false);    //   mix capital letters and small letters
        assertEquals(lgmgr.isValidPassword("EXamPLe"),false);    //   mix capital letters and small letters
        assertEquals(lgmgr.isValidPassword("exAMplE"),false);    //   mix capital letters and small letters

        assertEquals(lgmgr.isValidPassword("examPlE123#s"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("Example123#s"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("Example#s123"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("#Examples123"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("Examples123#"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("examplES123#"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("123exam&plES#"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("123#exampl&ES"),true);    // mix capital letters, small letters, numbers, and special characters @#$%^&+=
        assertEquals(lgmgr.isValidPassword("123EXamPLe#"),true);     // mix capital letters, small letters, numbers, and special characters @#$%^&+=

    }
    @After
    public void tearDown() throws Exception {

    }

}