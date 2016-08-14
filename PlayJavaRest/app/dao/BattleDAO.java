package dao;

import com.google.inject.Inject;
import models.Battle;
import models.Battle;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class BattleDAO {

    private JPAApi api;

    @Inject
    public BattleDAO(JPAApi api) {
        this.api = api;
    }

    @SuppressWarnings("unchecked")
    public List<Battle> findAllBattles() throws Throwable {
        return api.withTransaction(() -> (List<Battle>) api.em().createQuery("select b from Battle b").getResultList());
    }

    public Battle findBattleById(Long id) throws Throwable {
        try {
            return api.withTransaction(() -> (Battle) api.em()
                    .createQuery("select b from Battle b where b.id=:id")
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return null;
        }
    }

    public Battle createBattle(Battle battle) {
        api.withTransaction(() -> api.em().persist(battle));
        return battle;
    }

    public Battle updateBattle(Battle battle) throws Throwable {
        return api.withTransaction(() -> api.em().merge(battle));
    }

    public void removeBattle(Long id) throws Throwable {
        api.withTransaction(() -> {
            EntityManager em = api.em();
            try {
                em.remove(em.merge(findBattleById(id)));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

}
