package dao;

import com.google.inject.Inject;
import models.Country;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class CountryDAO {

    private JPAApi api;

    @Inject
    public CountryDAO(JPAApi api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public List<Country> findAllCountries() throws Throwable {
        return api.withTransaction(() -> (List<Country>) api.em().createQuery("select u from Country u").getResultList());
    }

    public Country findCountryById(Long id) throws Throwable {
        try {
            return api.withTransaction(() -> (Country) api.em()
                    .createQuery("select u from Country u where u.id=:id")
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    public int addPresident(Long id, String president) throws Throwable {
        try {
            return api.withTransaction(() -> api.em()
                    .createNativeQuery("update country SET president_id=:president where id=:id")
                    .setParameter("president", president)
                    .setParameter("id", id)
                    .executeUpdate());
        } catch (NoResultException e) {
            return  -1;
        }
    }

    public Country createCountry(Country country) {
        api.withTransaction(() -> api.em().persist(country));
        return country;
    }

    public Country updateCountry(Country country) throws Throwable {
        return api.withTransaction(() -> api.em().merge(country));
    }

    public void removeCountry(Long id) throws Throwable {
        api.withTransaction(() -> {
            EntityManager em = api.em();
            try {
                em.remove(em.merge(findCountryById(id)));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
