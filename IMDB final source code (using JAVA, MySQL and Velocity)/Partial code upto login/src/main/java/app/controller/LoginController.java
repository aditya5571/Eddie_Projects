package app.controller;

import app.controller.paths.Template;
import app.controller.paths.Web;
import io.javalin.http.Handler;
import java.util.Map;
import app.controller.utils.ViewUtil;

import static app.controller.utils.RequestUtil.*;
import static app.test.Main.*;


public class LoginController {

    public static Handler serveLoginPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("loggedOut", removeSessionAttrLoggedOut(ctx));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(ctx));
        ctx.render(Template.LOGIN, model);
    };

    public static Handler serveRegisterUserPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("loggedOut", removeSessionAttrLoggedOut(ctx));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(ctx));
        ctx.render(Template.REGISTERUSER, model);
    };

    public static Handler serveSignUpPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("loggedOut", removeSessionAttrLoggedOut(ctx));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(ctx));
        ctx.render(Template.SIGNUP, model);
    };

    public static Handler handleLoginPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        // check if authentication failed
        if (!UserController.authenticate(getQueryUsername(ctx), getQueryPassword(ctx), getQueryUserType(ctx))) {
            model.put("authenticationFailed", true);
        } else {
            // check if user is logging in as Admin
            if (getQueryUserType(ctx).equals("Admin")) {
                ctx.sessionAttribute("adminUser", getQueryUsername(ctx));
                model.put("adminUser", getQueryUsername(ctx));
            }
            // else make session for regular user login
            else {
                ctx.sessionAttribute("currentUser", getQueryUsername(ctx));
                model.put("currentUser", getQueryUsername(ctx));
            }
            model.put("authenticationSucceeded", true);
            if (getQueryLoginRedirect(ctx) != null) {
                ctx.redirect(getQueryLoginRedirect(ctx));
            }
        }
        ctx.render(Template.LOGIN, model);
    };

    public static Handler handleLogoutPost = ctx -> {
        ctx.sessionAttribute("currentUser", null);
        ctx.sessionAttribute("adminUser", null);
        ctx.sessionAttribute("loggedOut", "true");
        ctx.redirect(Web.LOGIN);
    };

    public static Handler handleSignUpPost = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);

        // When no existing user account is found, then only proceed to signup
        if (!userDAO.getUserAccount(getQuerySignUpUsername(ctx))) {
            requestDAO.storeRegisterUserRequest(ctx);
            model.put("noRequestsFound", false);
            model.put("requestSent", true);
            if (getQueryLoginRedirect(ctx) != null) {
                ctx.redirect(getQueryLoginRedirect(ctx));
            }
        }
        else {
            model.put("noRequestsFound", true);
        }
        ctx.render(Template.SIGNUP, model);
    };

}
