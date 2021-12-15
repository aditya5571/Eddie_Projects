package app.model;

public class Request {

    public final String requestid;
    public final String firstname;
    public final String lastname;
    public final String username;
    public final String password;
    public final String email;
    public final String gender;
    public final String country;
    public final String userType;


    public Request(String requestid, String username, String password, String email, String country, String gender, String firstname,
                   String lastname, String userType) {
        this.requestid = requestid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;
        this.gender = gender;
        this.userType = userType;
    }

    public String getRequestid() { return requestid; }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCountry() {
        return this.country;
    }

    public String getGender() {
        return this.gender;
    }

    public String getUserType() {
        return this.userType;
    }
}
