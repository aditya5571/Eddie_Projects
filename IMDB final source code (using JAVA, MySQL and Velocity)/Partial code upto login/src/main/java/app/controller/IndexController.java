package app.controller;

import app.controller.paths.Template;
import io.javalin.http.Handler;
import java.util.Map;
import app.controller.utils.ViewUtil;
import static app.test.Main.*;





public class IndexController {
    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.INDEX, model);
    };
}
