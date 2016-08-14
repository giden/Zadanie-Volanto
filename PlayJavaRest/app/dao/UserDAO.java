package dao;

import com.google.inject.Inject;
import models.User;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class UserDAO {

    private JPAApi api;

    @Inject
    public UserDAO(JPAApi api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() throws Throwable {
        return api.withTransaction(() -> (List<User>) api.em().createQuery("select u from user_ u").getResultList());
    }

    public User findUserByUsername(String username) throws Throwable {
        try {
            return api.withTransaction(() -> (User) api.em()
                    .createQuery("select u from user_ u where u.username=:username")
                    .setParameter("username", username)
                    .getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    public User createUser(User user) {
        System.out.println(user.role);
        api.withTransaction(() -> api.em().persist(user));
        return user;
    }

    public User updateUser(User user) throws Throwable {
        return api.withTransaction(() -> api.em().merge(user));
    }

    public void removeUser(String username) throws Throwable {
        api.withTransaction(() -> {
            EntityManager em = api.em();
            try {
                em.remove(em.merge(findUserByUsername(username)));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
