package models;

public class Auth {
    public String name;
    public String token;
    public String role;

    public Auth(String name, String role, String token) {
        this.name = name;
        this.token = token;
        this.role = role;
    }
}
