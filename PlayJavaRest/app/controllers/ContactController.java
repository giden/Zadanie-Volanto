package controllers;

import com.google.inject.Inject;
import dao.ContactDAO;
import models.Contact;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class ContactController extends Controller {

    @Inject
    private ContactDAO dao;

    public Result getContacts() {
        List<Contact> allContacts;

        try {
            allContacts = dao.findAllContacts();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(allContacts));
    }

    public Result getContact(Long id) {
        Contact contactById;

        try {
            contactById = dao.findContactById(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(contactById));
    }

    public Result addContact() {
        Contact contact = Json.fromJson(request().body().asJson(), Contact.class);

        contact = dao.createContact(contact);

        return created(Json.toJson(contact));
    }

    public Result updateContact() {
        Contact contact = Json.fromJson(request().body().asJson(), Contact.class);

        try {
            contact = dao.updateContact(contact);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return ok(Json.toJson(contact));
    }

    public Result deleteContact(Long id) {
        try {
            dao.removeContact(id);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return internalServerError();
        }
        return noContent();
    }
}
