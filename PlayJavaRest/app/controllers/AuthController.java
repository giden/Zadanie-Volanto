package controllers;

import com.google.inject.Inject;
import dao.AuthDAO;
import dao.UserDAO;
import models.Auth;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.AuthUtils;

public class AuthController extends Controller {

    @Inject
    private UserDAO dao;

    @Inject
    private AuthDAO authDao;

    @Inject
    private AuthUtils utils;

    public Result login() {
        User user = Json.fromJson(request().body().asJson(), User.class);

        try {
            authDao.cleanAuth(user.username);
            User userRetrieved = dao.findUserByUsername(user.username);

            if (userRetrieved.password.equals(user.password)) {
                Auth auth = new Auth(user.username, userRetrieved.role, utils.getToken());
                authDao.createAuth(auth);
                return ok(Json.toJson(auth));
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return unauthorized();
    }
}
