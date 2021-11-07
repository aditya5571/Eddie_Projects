package app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowDAO_ValidateSearchQueryForRandomShowId {

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
    void validateSearchQuery_Int_IfSingleString() {
        assertEquals(1, showDAO.validateSearchQueryForRandomShowId("Avengers", "1"),
                "Given search string should get required show id of 1");
    }

    @Test
    void validateSearchQuery_Int_IfStringContainsWhitespace() {
        assertEquals(1, showDAO.validateSearchQueryForRandomShowId("Captain America", "1"),
                "Given search string should get required show id of 1");
    }

    @Test
    void validateSearchQuery_Int_IfStringInCapitalLetters() {
        assertEquals(1, showDAO.validateSearchQueryForRandomShowId("CAPTAIN AMERICA", "1"),
                "Given search string should get required show id of 1");
    }

    @Test
    void validateSearchQuery_Int_IfStringInSmallLetters() {
        assertEquals(1, showDAO.validateSearchQueryForRandomShowId("iron man", "1"),
                "Given search string should get required show id of 1");
    }

    @Test
    void validateSearchQuery_Int_IfMixedString() {
        assertEquals(1, showDAO.validateSearchQueryForRandomShowId("IrOnMAn", "1"),
                "Given search string should get required show id of 1");
    }

    @Test
    void validateSearchQuery_Int_IfCorrectId() {
        assertEquals(3, showDAO.validateSearchQueryForRandomShowId("Avengers", "3"),
                "Given search string should get required show id of 3");
    }





    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void validateSearchQuery_Int_IfIncorrectId() {
        assertEquals(-1, showDAO.validateSearchQueryForRandomShowId("Avengers", "0"),
                "Given search string should return -1");
    }

    @Test
    void validateSearchQuery_Int_IfNegativeId() {
        assertEquals(-1, showDAO.validateSearchQueryForRandomShowId("Avengers", "-22"),
                "Given search string should return -1");
    }

    @Test
    void validateSearchQuery_Int_IfAboveHundredId() {
        assertEquals(-1, showDAO.validateSearchQueryForRandomShowId("Avengers", "187"),
                "Given search string should return -1");
    }

    @Test
    void validateSearchQuery_throwsException_IfIncorrectString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.validateSearchQueryForRandomShowId("%>#(587cq@^^", "2"),
                "Invalid search string should throw (IllegalArgumentException)");
    }

    @Test
    void validateSearchQuery_throwsException_IfIllogicalString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.validateSearchQueryForRandomShowId("^^$$$$^^", "2"),
                "Invalid search string should throw (IllegalArgumentException)");
    }

    @Test
    void validateSearchQuery_throwsException_IfAllFieldsIncorrect() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.validateSearchQueryForRandomShowId("^^$$$$^^", "-88"),
                "Invalid fields should throw (IllegalArgumentException)");
    }

//    @Test
//    void validateShowTitle_throwsException_IfMixedInvalidString() {
//        assertThrows(
//                IllegalArgumentException.class,
//                () -> showDAO.validateShowTitle("$$$$ Star wars $$$$"),
//                "Invalid show title should throw (IllegalArgumentException)");
//    }

}
