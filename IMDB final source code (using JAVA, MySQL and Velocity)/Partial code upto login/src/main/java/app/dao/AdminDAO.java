package app.dao;

import java.sql.SQLException;

import app.model.User;
import org.mindrot.jbcrypt.BCrypt;

import static app.test.Main.dbManager;
import static app.test.Main.userDAO;

public class AdminDAO {

    public void createFirstAdmin() throws SQLException {
        // First Admin to be registered into the system as 'alexander', to be added to our database
        String adminSalt = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";
        String adminPassword = "12345678";
        String adminHashedPassword = BCrypt.hashpw(adminPassword, adminSalt);

        // check if admin exists
        if (!userDAO.getUserAccount("alexander")) {
            checkAdminAccountValidity("alexander", adminSalt, "Admin");
            String query = "INSERT INTO `account` VALUES ('".concat("alexander").concat("', '")
                    .concat(adminHashedPassword).concat("', '").concat("alexander123@gmail.com").concat("', '")
                    .concat("Greek").concat("', '").concat("Male").concat("', '")
                    .concat("Alexander").concat("', '").concat("Xander").concat("', '")
                    .concat(String.valueOf(User.UserType.Admin)).concat("')");
            dbManager.getStatement().execute(query);
        }
    }

    // To be tested
    public boolean checkAdminAccountValidity(String tempUsername, String tempSalt, String tempUserType) {
        if (tempUsername == null) {
            return false;
        }
        if (!tempSalt.equals("$2a$10$h.dl5J86rGH7I8bD9bZeZe")) {
            return false;
        }
        if (!tempUserType.equals("Admin")) {
            return false;
        }
        return true;
    }

}