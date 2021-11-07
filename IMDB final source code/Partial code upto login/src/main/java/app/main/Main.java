package app.test;

import app.controller.*;
import app.dao.*;
import app.controller.localisation.Filters;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;
import app.data.DbManager;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

import java.sql.*;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;


public class Main {

    // Declare dependencies
    public static UserDAO userDAO;
    public static AdminDAO adminDAO;
    public static PCoDAO pCoDAO;
    public static ShowDAO showDAO;
    public static RequestDAO requestDAO;
    public static DbManager dbManager;

    public static void main(String[] args) throws SQLException {

        // Instantiate object and connect application to the database
        dbManager = new DbManager();
        dbManager.instantiateDatabaseConnection();

        // Instantiate dependencies
        userDAO = new UserDAO();
        adminDAO = new AdminDAO();
        pCoDAO = new PCoDAO();
        showDAO = new ShowDAO();
        requestDAO = new RequestDAO();

        // Run queries to add the first Admin and PCo account into the database
        adminDAO.createFirstAdmin();
        pCoDAO.createFirstPCo();

        // set user account request Id to be 0, will be incremented inside RequestDAO as requests are collected
        requestDAO.setRequestIdCount();

        // Get the Javalin application running on http://localhost:7777/
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(7777);

        app.routes(() -> {
            before(Filters.handleLocaleChange);
            get(Web.INDEX, IndexController.serveIndexPage);
            get(Web.REGISTERUSER, LoginController.serveRegisterUserPage);
            get(Web.LOGIN, LoginController.serveLoginPage);
            post(Web.LOGIN, LoginController.handleLoginPost);
            get(Web.SIGNUP, LoginController.serveSignUpPage);
            post(Web.SIGNUP, LoginController.handleSignUpPost);
            post(Web.LOGOUT, LoginController.handleLogoutPost);
            post(Web.SEARCH_RESULTS, SearchController.handleSearchQuery);
            get(Web.SEARCH_RESULTS, ShowController.fetchOneShow);
            get(Web.ONE_SHOW, ShowController.fetchOneShow);
            get(Web.DELETE_SHOW, ShowController.handleDeleteShow);
            get(Web.EDIT_SHOW, ShowController.serveEditShowPage);
            post(Web.EDIT_SHOW, ShowController.handleEditShow);
            get(Web.MANAGE_REQUESTS, UserController.serveManageRequestsPage);
            get(Web.ACCEPT_REQUEST, UserController.handleAcceptRequests);
            get(Web.REJECT_REQUEST, UserController.handleRejectRequests);
        });

        app.error(404, ViewUtil.notFound);
    }

}
