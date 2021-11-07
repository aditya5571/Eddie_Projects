package app.dao;

import app.model.Request;
import app.model.User;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import static app.controller.utils.RequestUtil.*;
import static app.test.Main.dbManager;

public class RequestDAO {

    private int requestIdCount;

    public void setRequestIdCount() {
        requestIdCount = 0;
    }

    public void storeRegisterUserRequest(Context ctx) throws SQLException {
        String query = "INSERT INTO `registration` VALUES ('".concat(String.valueOf(requestIdCount)).concat("', '")
                .concat(getQuerySignUpUsername(ctx)).concat("', '").concat(getQuerySignUpPassword(ctx)).concat("', '")
                .concat(getQueryEmail(ctx)).concat("', '").concat(getQueryCountry(ctx)).concat("', '")
                .concat(getQueryGender(ctx)).concat("', '").concat(getQueryFirstName(ctx)).concat("', '")
                .concat(getQueryLastName(ctx)).concat("', '").concat(String.valueOf(User.UserType.User)).concat("')");
        dbManager.getStatement().execute(query);
        requestIdCount++;
    }

    public boolean checkRequestSource(String email, String country) {
        if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$") && country.equals("Australia")) {
            return true;
        }
        else if (email == null || country == null) {
            throw new NullPointerException();
        }

        return false;
    }

    public ArrayList<Request> getAllRequests(Context ctx, Map<String, Object> model) throws SQLException {
        String query = "select * from imdb.registration;";
        ResultSet rs = dbManager.getStatement().executeQuery(query);
        ArrayList<Request> requestsFound = new ArrayList();
        while (rs.next()) {
            String requestId = rs.getString(1);
            String requestUsername = rs.getString(2);
            String requestPassword = rs.getString(3);
            String requestEmail = rs.getString(4);
            String requestCountry = rs.getString(5);
            String requestGender = rs.getString(6);
            String requestFirstname = rs.getString(7);
            String requestLastname = rs.getString(8);
            String requestUsertype = rs.getString(9);
            requestsFound.add(new Request(requestId, requestUsername, requestPassword, requestEmail, requestCountry, requestGender,
                    requestFirstname, requestLastname, requestUsertype));
        }
        if (requestsFound.size() == 0) {
            model.put("noRequestsFound", true);
        }
        return requestsFound;
    }

    public void acceptAccountRequestById(String id) throws SQLException {
        String query = "select * from imdb.registration where requestid = ".concat(id);
        ResultSet rs = dbManager.getStatement().executeQuery(query);
        if (rs.next()) {
            String salt = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";
            String newUserPassword = BCrypt.hashpw(rs.getString(3), salt);
            String insertQuery = "INSERT INTO `account` VALUES ('".concat(rs.getString(2)).concat("', '")
                    .concat(newUserPassword).concat("', '").concat(rs.getString(4)).concat("', '")
                    .concat(rs.getString(5)).concat("', '").concat(rs.getString(6)).concat("', '")
                    .concat(rs.getString(7)).concat("', '").concat(rs.getString(8)).concat("', '")
                    .concat(String.valueOf(User.UserType.User)).concat("')");
            dbManager.getStatement().executeUpdate(insertQuery);
            rejectAccountRequestById(id);
        }
    }

    public void rejectAccountRequestById(String id) throws SQLException {
        String query = "DELETE from imdb.registration where requestid = ".concat(id);
        dbManager.getStatement().executeUpdate(query);
    }

}
