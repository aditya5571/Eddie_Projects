package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.Show;
import io.javalin.http.Handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import static app.controller.utils.RequestUtil.getParamID;
import static app.test.Main.*;


public class ShowController {

    public static ArrayList<Show> shows;

    // Search the database and get all shows that include the given show title
    public static boolean findShow(String searchRequest, Map<String, Object> model) throws SQLException {
        if (searchRequest == null) {
            return false;
        }
        shows = showDAO.searchShowsByTitle(searchRequest);
        model.put("allShows", shows);
        if (shows.size() == 0) {
            return false;
        }
        return true;
    }

    public static Handler fetchOneShow = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("oneShow", showDAO.getShowByID(getParamID(ctx)));
        ctx.render(Template.ONE_SHOW, model);
    };

    public static Handler handleDeleteShow = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        showDAO.deleteShowById(getParamID(ctx));
        ctx.render(Template.DELETE_SHOW, model);
    };

    public static Handler serveEditShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("oneShow", showDAO.getShowByID(getParamID(ctx)));
        ctx.render(Template.EDIT_SHOW, model);
    };

    public static Handler handleEditShow = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("oneShow", showDAO.getShowByID(getParamID(ctx)));
        showDAO.editShowById(getParamID(ctx), ctx);
        model.put("editShowSuccessful", true);
        ctx.render(Template.EDIT_SHOW, model);
    };

}
