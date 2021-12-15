package app.controller.utils;


import io.javalin.http.Context;

public class RequestUtil {

    public static String getQueryLocale(Context ctx) {
        return ctx.queryParam("locale");
    }

    public static String getQueryFirstName(Context ctx) {
        return ctx.formParam("firstname");
    }

    public static String getQueryLastName(Context ctx) {
        return ctx.formParam("lastname");
    }

    public static String getQueryUsername(Context ctx) {
        return ctx.formParam("username");
    }

    public static String getQueryPassword(Context ctx) {
        return ctx.formParam("password");
    }

    public static String getQueryEmail(Context ctx) {
        return ctx.formParam("email");
    }

    public static String getQueryGender(Context ctx) {
        return ctx.formParam("gender");
    }

    public static String getQueryCountry(Context ctx) {
        return ctx.formParam("country");
    }

    public static String getQuerySignUpUsername(Context ctx) {
        return ctx.formParam("signUpUsername");
    }

    public static String getQuerySignUpPassword(Context ctx) {
        return ctx.formParam("signUpPassword");
    }

    public static String getQueryUserType(Context ctx) {
        return ctx.formParam("userType");
    }

    public static String getQuerySearch(Context ctx) {
        return ctx.formParam("searchRequest");
    }

    public static String getParamID(Context ctx) { return ctx.pathParam("id"); }

    public static String getEditedMovieTitle(Context ctx) { return ctx.formParam("editedTitle"); }

    public static String getEditedMovieGenre(Context ctx) {
        return ctx.formParam("editedGenre");
    }

    public static String getEditedMovieLength(Context ctx) {
        return ctx.formParam("editedLength");
    }

    public static String getEditedMovieYear(Context ctx) {
        return ctx.formParam("editedYear");
    }

    public static String getQueryLoginRedirect(Context ctx) {
        return ctx.queryParam("loginRedirect");
    }

    public static String getSessionLocale(Context ctx) {
        return (String) ctx.sessionAttribute("locale");
    }

    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }

    public static String getSessionCurrentAdminUser(Context ctx) { return (String) ctx.sessionAttribute("adminUser"); }

    public static boolean removeSessionAttrLoggedOut(Context ctx) {
        String loggedOut = ctx.sessionAttribute("loggedOut");
        ctx.sessionAttribute("loggedOut", null);
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Context ctx) {
        String loginRedirect = ctx.sessionAttribute("loginRedirect");
        ctx.sessionAttribute("loginRedirect", null);
        return loginRedirect;
    }

}
