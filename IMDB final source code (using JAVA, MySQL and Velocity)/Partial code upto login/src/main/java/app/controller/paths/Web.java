package app.controller.paths;

public class Web {
    // Web page paths
    public static final String INDEX = "/index";
    public static final String LOGIN = "/login";
    public static final String SIGNUP = "/signup";
    public static final String REGISTERUSER = "/registerUser";
    public static final String LOGOUT = "/logout";
    public static final String SEARCH_RESULTS = "/searchResults";
    public static final String ONE_SHOW = "/searchResults/:id";
    public static final String DELETE_SHOW = "/searchResults/deleteMovie/:id";
    public static final String EDIT_SHOW = "/searchResults/editMovie/:id";
    public static final String MANAGE_REQUESTS = "/manageRequests";
    public static final String ACCEPT_REQUEST = "/manageRequests/accept/:id";
    public static final String REJECT_REQUEST = "/manageRequests/reject/:id";
}