package app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PCoDAO_FindFeedbackForPCo {

    // Declaring private variables
    private PCoDAO pCoDAO;

    @BeforeAll
    void initialise() {
        pCoDAO = new PCoDAO();
    }



    /*
    BASIC TEST CASES
     */

    @Test
    void findFeedbackForPCo_String_IfCorrectDetails() {
        assertEquals("Review call allowed", pCoDAO.findFeedbackForPCo("PCo", "1"),
                "Correct details should return string 'Review call allowed'");
    }

    @Test
    void findFeedbackForPCo_Null_IfUsertypeSmallLetters() {
        assertNull(pCoDAO.findFeedbackForPCo("pco", "1"),
                "Incorrect usertype should return null");
    }

    @Test
    void findFeedbackForPCo_Null_IfUsertypeCapitalLetters() {
        assertNull(pCoDAO.findFeedbackForPCo("PCO", "1"),
                "Incorrect usertype should return null");
    }

    @Test
    void findFeedbackForPCo_Null_IfIncorrectUsertype() {
        assertNull(pCoDAO.findFeedbackForPCo("Admin", "1"),
                "Incorrect usertype should return null");
    }

    @Test
    void findFeedbackForPCo_Null_IfEmptyUsertypeString() {
        assertNull(pCoDAO.findFeedbackForPCo("", "1"),
                "Empty usertype string should return null");
    }

    @Test
    void findFeedbackForPCo_String_IfCorrectIdOne() {
        assertEquals("Review call allowed", pCoDAO.findFeedbackForPCo("PCo", "6"),
                "Correct Id should return string 'Review call allowed'");
    }

    @Test
    void findFeedbackForPCo_String_IfCorrectIdTwo() {
        assertEquals("Review call allowed", pCoDAO.findFeedbackForPCo("PCo", "43"),
                "Correct Id should return string 'Review call allowed'");
    }





    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void findFeedbackForPCo_Null_IfMixedUsertype() {
        assertNull(pCoDAO.findFeedbackForPCo("User123PCO", "1"),
                "Mixed usertype string should return null");
    }

    @Test
    void findFeedbackForPCo_Null_IfZeroId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pCoDAO.findFeedbackForPCo("PCo", "0"),
                "Invalid Id should throw (IllegalArgumentException)");
    }

    @Test
    void findFeedbackForPCo_Null_IfNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pCoDAO.findFeedbackForPCo("PCo", "-21"),
                "Negative Id should throw (IllegalArgumentException)");
    }

    @Test
    void findFeedbackForPCo_Null_IfMixedId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pCoDAO.findFeedbackForPCo("PCo", "vfo1j25324=-v9"),
                "Mixed Id should throw (IllegalArgumentException)");
    }

    @Test
    void findFeedbackForPCo_Null_IfNullId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> pCoDAO.findFeedbackForPCo("PCo", null),
                "Null Id should throw (IllegalArgumentException)");
    }

}
