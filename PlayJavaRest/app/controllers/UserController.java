package controllers;

import com.google.inject.Inject;
import dao.BattleDAO;
import dao.CountryDAO;
import dao.UserDAO;
import models.Battle;
import models.Role;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Date;
import java.util.List;

public class UserController extends Controller {

    final static String USERNAME = "username";
    private final static String USER_EXIST = "User with this username exist";
    @Inject
    private UserDAO dao;

    @Inject
    private CountryDAO countryDAO;

    @Inject
    private BattleDAO battleDAO;

    @Security.Authenticated(SecuredAdmin.class)
    public Result getUsers() {
        List<User> allUser;

        try {
            allUser = dao.findAllUsers();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(allUser));
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result getUser(String id) {
        User userById;

        try {
            userById = dao.findUserByUsername(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(userById));
    }

    public Result userExist(String id) {
        User userById;

        try {
            userById = dao.findUserByUsername(id);
            if (userById == null) {
                return ok();
            } else {
                return badRequest(USER_EXIST);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
    }

    @Security.Authenticated(Secured.class)
    public Result getLoginUser() {
        User userById;

        try {
            userById = dao.findUserByUsername((String) Http.Context.current().args.get(USERNAME));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(userById));
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result createUser(int countryId) {
        User user = getUserFromRequest();

        try {
            user.country = countryDAO.findCountryById((long) countryId);

            if (userNotExist(user.username)) {
                user = dao.createUser(user);
                return created(Json.toJson(user));
            } else {
                return badRequest(USER_EXIST);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
    }

    public Result register(int countryId) {
        User user = getUserFromRequest();
        try {
            user.country = countryDAO.findCountryById((long) countryId);

            if (user.role.equals(Role.ROLE_USER) && userNotExist(user.username)) {
                user = dao.createUser(user);
                return created(Json.toJson(user));
            } else {
                return badRequest(USER_EXIST);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result updateUser() {
        User user = getUserFromRequest();

        try {
            user = dao.updateUser(user);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(user));
    }

    @Security.Authenticated(SecuredAdmin.class)
    public Result deleteUser(String id) {
        try {
            dao.removeUser(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return noContent();
    }

    private boolean userNotExist(String username) throws Throwable {
        return dao.findUserByUsername(username) == null;
    }

    private User getUserFromRequest() {
        return Json.fromJson(request().body().asJson(), User.class);
    }

    @Security.Authenticated(Secured.class)
    public Result attack(Long battleId) {
        try {
            User userById = dao.findUserByUsername((String) Http.Context.current().args.get(USERNAME));
            Battle battle = battleDAO.findBattleById(battleId);

            Date now = new Date();
            if (battle.startTime.before(now) && battle.endTime.after(now)) {
                if (battle.aggressor.id.equals(userById.country.id)) {
                    battle.score -= userById.strength;
                } else {
                    battle.score += userById.strength;
                }

                return ok(Json.toJson(battleDAO.updateBattle(battle)));
            } else return badRequest("battle is inactive");

        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }

    }
}
