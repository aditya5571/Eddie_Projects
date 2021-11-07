package app.data;

import app.model.Request;
import app.model.User;
import io.javalin.http.Context;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import static app.controller.utils.RequestUtil.*;
import static app.test.Main.dbManager;

public class DbManager {

    private Statement st;

    public void instantiateDatabaseConnection() {
        try {
            // Establish connection to local database server instance, logged in using
            // the developer's account for MySQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/imdb", "root", "Jarvis5252");

            // Statement to execute MySQL query
            st = connection.createStatement();
            String query = "select * from person";
            st.executeQuery(query);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public Statement getStatement() {
        return st;
    }

}
