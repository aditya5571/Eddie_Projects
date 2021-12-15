package app.dao;

import app.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;
import org.mindrot.jbcrypt.BCrypt;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAO_CheckIfNullUser {

    // Declaring private variables
    private UserDAO userDAO;

    @BeforeAll
    void initialise() {
        userDAO = new UserDAO();
    }



    /*
    BASIC TEST CASES
     */

    @Test
    void checkIfNullUser_True_IfCorrectUser() {
        User user = (new User("Alexander", "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                BCrypt.hashpw("password", "$2a$10$h.dl5J86rGH7I8bD9bZeZe"), User.UserType.valueOf("Admin")));
        assertTrue(userDAO.checkIfNullUser(user), "Correct user should return true");
    }

    @Test
    void checkIfNullUser_True_IfIncorrectUsername() {
        User user = (new User("^%4ghjnbv$", "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                BCrypt.hashpw("password", "$2a$10$h.dl5J86rGH7I8bD9bZeZe"), User.UserType.valueOf("Admin")));
        assertTrue(userDAO.checkIfNullUser(user), "Incorrect username should return true");
    }



    /*
    EDGE/BORDERLINE TEST CASES
     */

    @Test
    void checkIfNullUser_True_IfNullUsername() {
        User user = (new User(null, "$2a$10$h.dl5J86rGH7I8bD9bZeZe",
                BCrypt.hashpw("password", "$2a$10$h.dl5J86rGH7I8bD9bZeZe"), User.UserType.valueOf("Admin")));
        assertTrue(userDAO.checkIfNullUser(user), "Null username should return true");
    }

    @Test
    void checkIfNullUser_throwsException_IfAllFieldsNull() {
        User user = null;
        assertThrows(
                NullPointerException.class,
                () -> userDAO.checkIfNullUser(user),
                "Null user should throw (NullPointerException)");
    }

}
