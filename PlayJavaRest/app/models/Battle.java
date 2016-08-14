package models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "aggressor_id")
    public Country aggressor;

    @ManyToOne
    @JoinColumn(name = "defender_id")
    public Country defender;

    public Long score = 10000L;

    public Date startTime;

    public Date endTime;

    private Battle() {
    }

    public Battle(Country defender, Country aggressor) {
        this.defender = defender;
        this.aggressor = aggressor;
        this.startTime = new Date();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(this.startTime);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        this.endTime = cal.getTime();
    }
}
