package app.model;

public class User {
    public final String username;
    public final String salt;
    public final String hashedPassword;
    public final UserType userType;

    public enum UserType {
        User,
        Admin,
        PCo
    }

    public User(String username, String salt, String hashedPassword, UserType userType) {
        this.username = username;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
    }

}
