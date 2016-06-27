package dao;

import models.Contact;
import play.db.jpa.JPA;

import java.util.List;

public class ContactDAO {

    @SuppressWarnings("unchecked")
    public List<Contact> findAllContacts() throws Throwable {
        return JPA.withTransaction(() -> (List<Contact>) JPA.em().createQuery("select c from Contact c").getResultList());
    }

    public Contact findContactById(Long id) throws Throwable {
        return JPA.withTransaction(() -> (Contact) JPA.em()
                .createQuery("select c from Contact c where c.id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public Contact createContact(Contact contact) {
        JPA.withTransaction(() -> JPA.em().persist(contact));
        return contact;
    }

    public Contact updateContact(Contact contact) throws Throwable {
        return JPA.withTransaction(() -> JPA.em().merge(contact));
    }

    public void removeContact(Long id) throws Throwable {
        JPA.em().remove(findContactById(id));
    }

}
