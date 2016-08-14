package models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    public String name;

    @Column(name = "currency_name")
    public String currencyName;

    @Column(name = "parity_rate")
    public String parityRate;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    public Set<User> users;

    @Column(name = "attacking-battles")
    @OneToMany(mappedBy = "aggressor", fetch = FetchType.EAGER)
    public Set<Battle> attackingBattles;

    @Column(name = "defending-battles")
    @OneToMany(mappedBy = "defender", fetch = FetchType.EAGER)
    public Set<Battle> defendingBattles;

    @OneToOne()
    @JoinColumn(name = "president_id")
    public User president;
}
