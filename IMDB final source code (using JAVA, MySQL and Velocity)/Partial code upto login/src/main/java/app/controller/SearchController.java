package app.controller;

import app.controller.paths.Template;
import io.javalin.http.Handler;
import java.util.Map;
import app.controller.utils.ViewUtil;

import static app.controller.utils.RequestUtil.*;

public class SearchController {

    public static Handler handleSearchQuery = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        if (!ShowController.findShow(getQuerySearch(ctx), model)) {
            model.put("noShowsFound", true);
        }
        ctx.render(Template.SEARCH_RESULTS, model);
    };

}
