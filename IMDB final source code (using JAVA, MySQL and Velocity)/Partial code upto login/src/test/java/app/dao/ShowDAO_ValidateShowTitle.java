package app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowDAO_ValidateShowTitle {

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
    void validateShowTitle_True_IfSingleString() {
        assertTrue(showDAO.validateShowTitle("star"), "Given title should match regex");
    }

    @Test
    void validateShowTitle_True_IfStringContainsWhitespace() {
        assertTrue(showDAO.validateShowTitle("Star wars"), "Given title should match regex");
    }

    @Test
    void validateShowTitle_True_IfStringContainsCapitalLetters() {
        assertTrue(showDAO.validateShowTitle("StAR WARs"), "Given title should match regex");
    }

    @Test
    void validateShowTitle_True_IfStringContainsSmallLetters() {
        assertTrue(showDAO.validateShowTitle("star wars"), "Given title should match regex");
    }



    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void validateShowTitle_True_IfStringContainsNumerals() {
        assertTrue(showDAO.validateShowTitle("star wars 3 X6"), "Given title should match regex");
    }

    @Test
    void validateShowTitle_True_IfEmptyString() {
        assertTrue(showDAO.validateShowTitle(""), "Given title should match regex");
    }

    @Test
    void validateShowTitle_throwsException_IfInvalidString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.validateShowTitle("Batman^^56543$&&*% 22@@  "),
                "Invalid show title should throw (IllegalArgumentException)");
    }

    @Test
    void validateShowTitle_throwsException_IfMixedInvalidString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.validateShowTitle("$$$$ Star wars $$$$"),
                "Invalid show title should throw (IllegalArgumentException)");
    }

}
