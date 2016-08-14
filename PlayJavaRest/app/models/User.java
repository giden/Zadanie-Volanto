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

    public Boolean locked;

    public Integer strength;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    public Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "ideology_name")
    public Ideology ideology;

    @ManyToOne
    @JoinColumn(name = "country_id")
    public Country country;

    @OneToOne(mappedBy="president")
    private Country countryPresident;
}
