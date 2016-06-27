package models;

import javax.persistence.*;

@Entity(name = "user_")
public class User {

    @Id
    public String username;

    public String password;

    public String email;

    public String name;

    public String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    public Role role;
}
