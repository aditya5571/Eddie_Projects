package app.dao;

import app.model.Show;
import io.javalin.http.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import static app.controller.utils.RequestUtil.*;
import static app.test.Main.dbManager;

public class ShowDAO {

    // get an ArrayList of all shows included in the search
    public ArrayList<Show> searchShowsByTitle(String searchRequest) throws SQLException {
        // get all shows matching the requested title from the database
        String query = "select * from imdb.show where LOWER(show_title) like '%"
                .concat(searchRequest.toLowerCase(Locale.ROOT)).concat("%';");
        ResultSet rs = dbManager.getStatement().executeQuery(query);
        ArrayList<Show> showsFound = new ArrayList();
        while (rs.next()) {
            showsFound.add((new Show(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8))));
        }

        return showsFound;
    }

    // find a single show by showID
    public Show getShowByID(String id) throws SQLException {
        String query = "select * from imdb.show where showid=".concat(id);
        ResultSet rs = dbManager.getStatement().executeQuery(query);
        while (rs.next()) {
            return (new Show(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6),
                    rs.getString(7), rs.getString(8)));
        }

        return null;
    }

    // delete a single show by showID
    public void deleteShowById(String id) throws SQLException {
        String query = "DELETE FROM imdb.credits_roll WHERE show_id=".concat(id);
        dbManager.getStatement().executeUpdate(query);
        query = "DELETE FROM imdb.show WHERE showid=".concat(id);
        dbManager.getStatement().executeUpdate(query);
    }

    // Edit the entry of a single show by showID
    public void editShowById(String id, Context ctx) throws SQLException {
        String query = "UPDATE imdb.show SET show_title='".concat(getEditedMovieTitle(ctx)).concat("', genre='")
            .concat(getEditedMovieGenre(ctx)).concat("', length='").concat(getEditedMovieLength(ctx))
            .concat("', year='").concat(getEditedMovieYear(ctx)).concat("' WHERE showid=").concat(id);
        dbManager.getStatement().executeUpdate(query);
    }

    // 1) Stub method for testing user story related to: editing a show title
    public boolean validateShowTitle(String editedTitle) {
        if (editedTitle.matches("^[a-zA-Z0-9_\\s]*$")) {
            return true;
        }
        else {
            throw new IllegalArgumentException("Invalid show title characters");
        }
    }

    public boolean confirmDeleteShowRequirements(String id, String adminUsername) {
        int totalShows = 3;
        if ((adminUsername.equals("alexander")) && (Integer.parseInt(id) >= 0) && (Integer.parseInt(id) <= totalShows)) {
            return true;
        }
        else {
            throw new IllegalArgumentException("Given admin or show id was invalid");
        }
    }

    public int validateSearchQueryForRandomShowId(String searchQuery, String id) {
        int[] totalShows = new int[] { 1, 2, 3 };

        if ((searchQuery.matches("^[a-zA-Z0-9_\\s]*$")) && (searchQuery.length() > 0) &&
                (searchQuery.length() < 20)) {
            for (int i = 1; i <= totalShows.length; i++) {
                if (i == Integer.parseInt(id)) {
                    return Integer.parseInt(id);
                }
            }
            return -1;
        }
        else {
            throw new IllegalArgumentException("Given search query was invalid");
        }
    }

    public double rectifyShowLength(double length) {
        BigDecimal bd = new BigDecimal(length).setScale(2, RoundingMode.HALF_UP);
        if (length > 0) {
            double rectifiedLength = bd.doubleValue();
            return rectifiedLength;
        }
        else {
            throw new IllegalArgumentException("Given show length was invalid.");
        }
    }

}
