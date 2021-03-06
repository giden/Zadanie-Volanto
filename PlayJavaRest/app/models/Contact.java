package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    public String name;

    public String surname;

    public String firmname;

    public String email;

    public String phone;
}
