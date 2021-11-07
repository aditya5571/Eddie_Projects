package app.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;




@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AdminDAO_CheckAdminAccountValidity {

    // Declaring private variables
    private AdminDAO adminDAO;

    @BeforeAll
    void initialise() {
        adminDAO = new AdminDAO();
    }



    /*
    BASIC TEST CASES
     */

    @Test
    void checkAdminAccountValidity_True_IfCorrectDetails() {
        assertTrue(adminDAO.checkAdminAccountValidity("alexander", "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                "Admin"), "Valid admin account details should return true");
    }

    @Test
    void checkAdminAccountValidity_True_IfIncorrectUsername() {
        assertTrue(adminDAO.checkAdminAccountValidity("drilbit", "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                "Admin"), "Incorrect username should return true");
    }

    @Test
    void checkAdminAccountValidity_False_IfIncorrectSalt() {
        assertFalse(adminDAO.checkAdminAccountValidity("alexander", "uaweiorhtnbvio2j35inonvf",
                "Admin"), "Incorrect salt should return false");
    }

    @Test
    void checkAdminAccountValidity_False_IfIncorrectUserType() {
        assertFalse(adminDAO.checkAdminAccountValidity("alexander", "uaweiorhtnbvio2j35inonvf",
                "Critic"), "Incorrect usertype should return false");
    }



    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void checkAdminAccountValidity_False_IfUsernameNull() {
        assertFalse(adminDAO.checkAdminAccountValidity(null, "---------",
                "Shopper"), "Given null username should return false");
    }

    @Test
    void checkAdminAccountValidity_False_IfAllFieldsNull() {
        assertFalse(adminDAO.checkAdminAccountValidity(null, null,
                null), "All null fields should return false");
    }

    @Test
    void checkAdminAccountValidity_False_IfAllIncorrectDetails() {
        assertFalse(adminDAO.checkAdminAccountValidity("^%4ghjnbv$", "---------",
                "Shopper"), "All incorrect details should return false");
    }

}
