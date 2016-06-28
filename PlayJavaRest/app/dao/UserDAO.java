package dao;

import com.google.inject.Inject;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public class UserDAO {

    JPAApi api;

    @Inject
    public UserDAO(JPAApi api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() throws Throwable {
        return api.withTransaction(() -> (List<User>) api.em().createQuery("select u from user_ u").getResultList());
    }

    public User findUserById(Long id) throws Throwable {
        return api.withTransaction(() -> (User) api.em()
                .createQuery("select u from user_ u u.id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public User findUserByUsername(String name) throws Throwable {
        return api.withTransaction(() -> (User) api.em()
                .createQuery("select u from user_ u where u.username=:name")
                .setParameter("name", name)
                .getSingleResult());
    }

    public User createUser(User user) {
        api.withTransaction(() -> api.em().persist(user));
        return user;
    }

    public User updateUser(User user) throws Throwable {
        return api.withTransaction(() -> api.em().merge(user));
    }

    public void removeUser(Long id) throws Throwable {
        api.withTransaction(() -> {
            EntityManager em = api.em();
            try {
                em.remove(em.merge(findUserById(id)));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
