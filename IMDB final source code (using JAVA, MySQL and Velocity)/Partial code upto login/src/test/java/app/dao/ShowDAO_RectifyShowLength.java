package app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowDAO_RectifyShowLength {

    // Declaring private variables
    private ShowDAO showDAO;

    @BeforeAll
    void initialise() {
        showDAO = new ShowDAO();
    }



    /*
    BASIC TEST CASES
     */

    @Test
    void rectifyShowLength_Double_IfLongValueOne() {
        assertEquals(3.33, showDAO.rectifyShowLength(3.3333345676543),
                "Given length should return 3.33");
    }

    @Test
    void rectifyShowLength_Double_IfLongValueTwo() {
        assertEquals(5.30, showDAO.rectifyShowLength(5.2988173095),
                "Given length should return 5.30");
    }

    @Test
    void rectifyShowLength_Double_IfLongValueThree() {
        assertEquals(9.00, showDAO.rectifyShowLength(8.9999999999999999999),
                "Given length should return 9.00");
    }

    @Test
    void rectifyShowLength_Double_IfLongValueFour() {
        assertEquals(0.62, showDAO.rectifyShowLength(0.618888888888),
                "Given length should return 0.62");
    }





    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void rectifyShowLength_Double_IfIntValue() {
        assertEquals(450.00, showDAO.rectifyShowLength(450),
                "Given length should return 450.00");
    }

    @Test
    void rectifyShowLength_Double_IfSmallValueOne() {
        assertEquals(0.01, showDAO.rectifyShowLength(0.01),
                "Given length should return 0.01");
    }

    @Test
    void rectifyShowLength_throwsException_IfIncorrectValue() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.rectifyShowLength(0),
                "Invalid show length should throw (IllegalArgumentException)");
    }

    @Test
    void rectifyShowLength_throwsException_IfNegativeValueOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.rectifyShowLength(-3.556),
                "Invalid show length should throw (IllegalArgumentException)");
    }

    @Test
    void rectifyShowLength_throwsException_IfNegativeValueTwo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.rectifyShowLength(-343.22888234567),
                "Invalid show length should throw (IllegalArgumentException)");
    }

}
