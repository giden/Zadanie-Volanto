package models;

import javax.persistence.*;

@Entity
public class Auth {

    @Id
    public String username;
    public String token;

    @Enumerated(EnumType.STRING)
    public Role role;

    public Auth() {
    }

    public Auth(String user, Role role, String token) {
        this.username = user;
        this.token = token;
        this.role = role;
    }
}
