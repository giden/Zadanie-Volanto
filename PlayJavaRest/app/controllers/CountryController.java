package controllers;

import com.google.inject.Inject;
import dao.BattleDAO;
import dao.CountryDAO;
import dao.UserDAO;
import models.Battle;
import models.Country;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

@Security.Authenticated(Secured.class)
public class CountryController extends Controller {

    private static final String NO_COUNTRY = "No such country";

    @Inject
    private CountryDAO dao;

    @Inject
    private BattleDAO battleDAO;

    @Inject
    private UserDAO userDAO;

    public Result getCountries() {

        List<Country> allCountries;
        try {
            allCountries = dao.findAllCountries();
            return ok(Json.toJson(allCountries));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return internalServerError();
    }

    public Result getCountry(Long id) {
        Country countryById;

        try {
            countryById = dao.findCountryById(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(countryById));
    }

    @Security.Authenticated(Secured.class)
    public Result callBattle(String aggressorId, String defenderId) {

        try {
            User userById = userDAO.findUserByUsername(
                    (String) Http.Context.current().args.get(UserController.USERNAME));

            Country aggressor = dao.findCountryById(Long.valueOf(aggressorId));
            if (aggressor != null && aggressor.president.username.equals(userById.username)) {
                Country defender = dao.findCountryById(Long.valueOf(defenderId));

                if (defender != null) {
                    Battle battle = battleDAO.createBattle(new Battle(defender, aggressor));
                    return ok(Json.toJson(battle));
                } else {
                    return badRequest(NO_COUNTRY);
                }
            } else {
                return unauthorized();
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result getBattles() {
        Country countryById;

        try {
            User userById = userDAO.findUserByUsername(
                    (String) Http.Context.current().args.get(UserController.USERNAME));

            countryById = dao.findCountryById(userById.country.id);

            if (countryById != null) {
                countryById.attackingBattles.addAll(countryById.defendingBattles);
            } else {
                return unauthorized();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(countryById.attackingBattles));
    }

    @Security.Authenticated(Secured.class)
    public Result getBattle(Long battleId) {
        Country countryById;

        try {
            User userById = userDAO.findUserByUsername(
                    (String) Http.Context.current().args.get(UserController.USERNAME));

            countryById = dao.findCountryById(userById.country.id);

            Battle battle = battleDAO.findBattleById(battleId);

            if (countryById.attackingBattles.contains(battle) || countryById.defendingBattles.contains(battle)) {
                return ok(Json.toJson(battle));
            }
            else return unauthorized();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
    }


    @Security.Authenticated(SecuredAdmin.class)
    public Result createCountry() {
        Country country = Json.fromJson(request().body().asJson(), Country.class);

        country = dao.createCountry(country);

        return created(Json.toJson(country));
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result updateCountry() {
        Country country = Json.fromJson(request().body().asJson(), Country.class);

        try {
            country = dao.updateCountry(country);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(country));
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result deleteCountry(Long id) {
        try {
            dao.removeCountry(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return noContent();
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result addPresident(String presidentName, String countryId) {
        try {
            dao.addPresident(Long.valueOf(countryId), presidentName);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return noContent();
    }

}
