package models;

public class Auth {
    public String user;
    public String token;
    public String role;

    public Auth(String user, String role, String token) {
        this.user = user;
        this.token = token;
        this.role = role;
    }
}
