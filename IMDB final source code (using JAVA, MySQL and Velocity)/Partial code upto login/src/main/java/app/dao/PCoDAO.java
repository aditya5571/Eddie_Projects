package app.dao;

import app.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

import static app.test.Main.dbManager;
import static app.test.Main.userDAO;

public class PCoDAO {
    public void createFirstPCo() throws SQLException {
        // First PCo to be registered into the system as 'jokovich', to be added to our database
        String pCoSalt = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";
        String pCoPassword = "12345678";
        String pCoHashedPassword = BCrypt.hashpw(pCoPassword, pCoSalt);

        // check if pco exists
        if (!userDAO.getUserAccount("jokovich")) {
            String query = "INSERT INTO `account` VALUES ('".concat("jokovich").concat("', '")
                    .concat(pCoHashedPassword).concat("', '").concat("jokovich123@gmail.com").concat("', '")
                    .concat("New Zealand").concat("', '").concat("Male").concat("', '")
                    .concat("Jokovich").concat("', '").concat("Vich").concat("', '")
                    .concat(String.valueOf(User.UserType.PCo)).concat("')");
            dbManager.getStatement().execute(query);
        }
    }

    public String findFeedbackForPCo(String userType, String id) {
        if (!userType.equals("PCo")) {
            return null;
        }
        else {
            if (Integer.parseInt(id) > 0) {
                return "Review call allowed";
            }
            else {
                throw new IllegalArgumentException("Provided Id string was invalid.");
            }
        }
    }

}
