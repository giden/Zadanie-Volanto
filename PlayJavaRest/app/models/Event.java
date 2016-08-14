package models;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    public String name;
    public Timestamp startTime;
    public Timestamp endTime;
    public String description;

    @Enumerated(EnumType.STRING)
    public EventType type;
}
