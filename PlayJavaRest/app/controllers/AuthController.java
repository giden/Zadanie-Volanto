package controllers;

import com.google.inject.Inject;
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
    private AuthUtils utils;

    public Result login() {
        User user = Json.fromJson(request().body().asJson(), User.class);

        try {
            User userRetrieved = dao.findUserByUsername(user.username);

            if (userRetrieved.password.equals(user.password)) {
                return ok(Json.toJson(new Auth(user.username, userRetrieved.role.name(), utils.getToken())));
            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return unauthorized();
    }
}
