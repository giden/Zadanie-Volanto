package dao;

import com.google.inject.Inject;
import models.Event;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.List;

public class EventDAO {

    private JPAApi api;

    @Inject
    public EventDAO(JPAApi api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public List<Event> findAllEvents() throws Throwable {
        return api.withTransaction(() -> (List<Event>) api.em().createQuery("select c from Event c").getResultList());
    }

    public Event findEventById(Long id) throws Throwable {
        return api.withTransaction(() -> (Event) api.em()
                .createQuery("select c from Event c where c.id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public Event createEvent(Event event) {
        api.withTransaction(() -> api.em().persist(event));
        return event;
    }

    public Event updateEvent(Event event) throws Throwable {
        return api.withTransaction(() -> api.em().merge(event));
    }

    public void removeEvent(Long id) throws Throwable {
        api.withTransaction(() -> {
            EntityManager em = api.em();
            try {
                em.remove(em.merge(findEventById(id)));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
