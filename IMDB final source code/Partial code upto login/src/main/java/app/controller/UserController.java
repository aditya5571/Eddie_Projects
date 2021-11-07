package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.User;
import io.javalin.http.Handler;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Map;

import static app.controller.utils.RequestUtil.getParamID;
import static app.controller.utils.RequestUtil.getQueryLoginRedirect;
import static app.test.Main.*;





public class UserController {

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String username, String password, String userType) throws SQLException {
        if (username == null || password == null || userType == null) {
            return false;
        }
        User user = userDAO.getUserByUsername(username, userType);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.salt);
        return (hashedPassword.equals(user.hashedPassword)) && (User.UserType.valueOf(userType).equals(user.userType));
    }

    public static Handler serveManageRequestsPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("allRequests", requestDAO.getAllRequests(ctx, model));
        ctx.render(Template.MANAGE_REQUESTS, model);
    };

    public static Handler handleAcceptRequests = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        requestDAO.acceptAccountRequestById(getParamID(ctx));
        model.put("requestAccepted", true);
        if (getQueryLoginRedirect(ctx) != null) {
            ctx.redirect(getQueryLoginRedirect(ctx));
        }
        ctx.render(Template.MANAGE_REQUESTS, model);
    };

    public static Handler handleRejectRequests = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        requestDAO.rejectAccountRequestById(getParamID(ctx));
        model.put("requestRejected", true);
        if (getQueryLoginRedirect(ctx) != null) {
            ctx.redirect(getQueryLoginRedirect(ctx));
        }
        ctx.render(Template.MANAGE_REQUESTS, model);
    };

}
