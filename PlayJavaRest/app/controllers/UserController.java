package controllers;

import com.google.inject.Inject;
import dao.UserDAO;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class UserController extends Controller {

    @Inject
    private UserDAO dao;

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

    public Result getUser(Long id) {
        User userById;

        try {
            userById = dao.findUserById(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(userById));
    }

    public Result createUser() {
        User user = Json.fromJson(request().body().asJson(), User.class);

        user = dao.createUser(user);

        return created(Json.toJson(user));
    }

    public Result updateUser() {
        User user = Json.fromJson(request().body().asJson(), User.class);

        try {
            user = dao.updateUser(user);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(user));
    }

    public Result deleteUser(Long id) {
        try {
            dao.removeUser(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return noContent();
    }
}
