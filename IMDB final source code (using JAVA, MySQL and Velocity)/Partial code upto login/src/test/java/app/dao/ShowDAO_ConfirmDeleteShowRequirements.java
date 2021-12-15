package app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowDAO_ConfirmDeleteShowRequirements {

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
    void confirmDeleteShowRequirements_True_IfCorrectStrings() {
        assertTrue(showDAO.confirmDeleteShowRequirements("0", "alexander"),
                "Correct show id and admin username should return true");
    }

    @Test
    void confirmDeleteShowRequirements_True_IfDifferentId() {
        assertTrue(showDAO.confirmDeleteShowRequirements("2", "alexander"),
                "Correct show id and admin username should return true");
    }





    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void confirmDeleteShowRequirements_throwsException_IfIncorrectId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("4", "alexander"),
                "Out of bounds show id should throw (IllegalArgumentException)");
    }

    @Test
    void confirmDeleteShowRequirements_throwsException_IfAboveHundreId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("505", "alexander"),
                "Out of bounds show id should throw (IllegalArgumentException)");
    }

    @Test
    void confirmDeleteShowRequirements_throwsException_IfNegativeId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("-33", "alexander"),
                "Out of bounds show id should throw (IllegalArgumentException)");
    }

    @Test
    void confirmDeleteShowRequirements_throwsException_IfMixedId() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("-64Boat", "alexander"),
                "Mixed show id string should throw (IllegalArgumentException)");
    }

    @Test
    void confirmDeleteShowRequirements_throwsException_IfIncorrectAdminUsername() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("0", "Drillbit"),
                "Out of bounds show id should throw (IllegalArgumentException)");
    }

    @Test
    void confirmDeleteShowRequirements_throwsException_IfMixedAdminUsername() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("0", "Tyler544"),
                "Out of bounds show id should throw (IllegalArgumentException)");
    }

    @Test
    void confirmDeleteShowRequirements_throwsException_IfCorrectCapitalizedAdminUsername() {
        assertThrows(
                IllegalArgumentException.class,
                () -> showDAO.confirmDeleteShowRequirements("0", "ALEXANDER"),
                "Out of bounds show id should throw (IllegalArgumentException)");
    }

}
