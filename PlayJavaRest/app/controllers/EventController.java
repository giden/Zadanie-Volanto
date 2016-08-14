package controllers;

import com.google.inject.Inject;
import dao.EventDAO;
import models.Event;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.List;

@Security.Authenticated(Secured.class)
public class EventController extends Controller {

    @Inject
    private EventDAO dao;

    public Result getEvents() {

        List<Event> allEvents;
        try {
            allEvents = dao.findAllEvents();
            return ok(Json.toJson(allEvents));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return internalServerError();
    }

    public Result getEvent(Long id) {
        Event eventById;

        try {
            eventById = dao.findEventById(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(eventById));
    }

    public Result createEvent() {
        Event event = Json.fromJson(request().body().asJson(), Event.class);

        event = dao.createEvent(event);

        return created(Json.toJson(event));
    }

    public Result updateEvent() {
        Event event = Json.fromJson(request().body().asJson(), Event.class);

        try {
            event = dao.updateEvent(event);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(event));
    }

    public Result deleteEvent(Long id) {
        try {
            dao.removeEvent(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return noContent();
    }
}
