package app.controller.utils;

import app.controller.ShowController;
import app.controller.localisation.MessageBundle;
import app.controller.paths.Template;
import io.javalin.http.Context;
import io.javalin.http.ErrorHandler;
import java.util.HashMap;
import java.util.Map;

import static app.controller.utils.RequestUtil.*;



public class ViewUtil {

    public static Map<String, Object> baseModel(Context ctx) {
        // put the variables/objects in the base model, meaning they are stored for a full session
        Map<String, Object> model = new HashMap<>();
        model.put("msg", new MessageBundle(getSessionLocale(ctx)));
        model.put("currentUser", getSessionCurrentUser(ctx));
        model.put("adminUser", getSessionCurrentAdminUser(ctx));
        return model;
    }

    public static ErrorHandler notFound = ctx -> {
        ctx.render(Template.NOT_FOUND, baseModel(ctx));
    };

}
