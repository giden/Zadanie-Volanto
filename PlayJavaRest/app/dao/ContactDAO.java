package dao;

import com.google.inject.Inject;
import models.Contact;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.libs.Json;

import javax.persistence.EntityManager;
import java.util.List;

public class ContactDAO {

    JPAApi api;

    @Inject
    public ContactDAO(JPAApi api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public List<Contact> findAllContacts() throws Throwable {
        return api.withTransaction(() -> (List<Contact>) api.em().createQuery("select c from Contact c").getResultList());
    }

    public Contact findContactById(Long id) throws Throwable {
        return api.withTransaction(() -> (Contact) api.em()
                .createQuery("select c from Contact c where c.id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public Contact createContact(Contact contact) {
        api.withTransaction(() -> api.em().persist(contact));
        return contact;
    }

    public Contact updateContact(Contact contact) throws Throwable {
        return api.withTransaction(() -> api.em().merge(contact));
    }

    public void removeContact(Long id) throws Throwable {
        api.withTransaction(() -> {
            EntityManager em = api.em();
            try {
                em.remove(em.merge(findContactById(id)));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
