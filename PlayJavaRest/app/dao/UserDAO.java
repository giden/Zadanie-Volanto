package dao;

import models.User;
import play.db.jpa.JPA;

import java.util.List;

public class UserDAO {

    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() throws Throwable {
        return JPA.withTransaction(() -> (List<User>) JPA.em().createQuery("select u from user_ u").getResultList());
    }

    public User findUserById(Long id) throws Throwable {
        return JPA.withTransaction(() -> (User) JPA.em()
                .createQuery("select u from user_ u u.id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public User createUser(User user) {
        JPA.withTransaction(() -> JPA.em().persist(user));
        return user;
    }

    public User updateUser(User user) throws Throwable {
        return JPA.withTransaction(() -> JPA.em().merge(user));
    }

    public void removeUser(Long id) throws Throwable {
        JPA.em().remove(findUserById(id));
    }

}
