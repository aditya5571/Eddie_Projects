package app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import app.controller.utils.ViewUtil;
import app.model.User;

import static app.test.Main.dbManager;

public class UserDAO {

    // get required user details from database, and make a temp user to return
    public User getUserByUsername(String username, String userType) throws SQLException {
        String query = "select * from account where username='".concat(username).concat("' and user_type='")
                .concat(userType).concat("'");
        ResultSet rs = dbManager.getStatement().executeQuery(query);
        if (rs.next()) {
            String tempUsername = rs.getString(1);
            String tempSalt = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";
            String tempHashedPassword = rs.getString(2);
            String tempUserType = rs.getString(8);
            return (new User(tempUsername, tempSalt, tempHashedPassword, User.UserType.valueOf(tempUserType)));
        }

        return null;
    }

    // find existing user account by username
    public boolean getUserAccount(String username) throws SQLException {
        String query = "select * from account where username='".concat(username).concat("'");
        ResultSet rs = dbManager.getStatement().executeQuery(query);
        if (rs.next()) {
            return true;
        }

        return false;
    }

    // check if passed user is null
    public boolean checkIfNullUser(User user) {
        if (user != null) {
            return true;
        }
        else {
            throw new NullPointerException(
                    "No user found in database. Null user");
        }
    }

//    public void confirmLogOutForUserType(String userType) {
//
//    }

}
