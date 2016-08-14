package dao;

import com.google.inject.Inject;
import models.Auth;
import play.db.jpa.JPAApi;

public class AuthDAO {

    private JPAApi api;

    @Inject
    public AuthDAO(JPAApi api) {
        this.api = api;
    }

    public Auth findAuthByUsername(String username) throws Throwable {
        return api.withTransaction(() -> (Auth) api.em()
                .createQuery("select a from Auth a where a.username=:username")
                .setParameter("username", username)
                .getSingleResult());
    }

    public Auth createAuth(Auth auth) {
        api.withTransaction(() -> api.em().persist(auth));
        return auth;
    }

    public void cleanAuth(String username) {
        api.withTransaction(() -> api.em().createQuery("DELETE FROM Auth a where a.username=:username")
                .setParameter("username", username)
                .executeUpdate());

    }
}
