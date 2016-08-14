package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.binary.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;


public class UserControllerTest {

    private static final String USERNAME = "admin";

    private WSClient ws = play.libs.ws.WS.newClient(3333);

    private String user;
    private String token;

    @Before
    public void setUp() throws Exception {
        WSRequest request = ws.url("http://localhost:9000/auth/login");

        ObjectNode node = Json.newObject();
        node.put("username", USERNAME);
        node.put("password", "123");

        CompletionStage<JsonNode> jsonPromise = request
                .setHeader("Content-Type", "application/json")
                .post(node)
                .thenApply(WSResponse::asJson);

        JsonNode jsonNode = jsonPromise.toCompletableFuture().get();

        user = jsonNode.get("username").textValue();
        token = jsonNode.get("token").textValue();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getUsers() throws Exception {
        WSRequest request = ws.url("http://localhost:9000/users");

        CompletionStage<JsonNode> jsonPromise = request.setHeader(
                "Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .get()
                .thenApply(WSResponse::asJson);

        JsonNode jsonNode = jsonPromise.toCompletableFuture().get();
        assertEquals(USERNAME, jsonNode.get(0).get("username").textValue());
    }

    @Test
    public void getUser() throws Exception {
        WSRequest request = ws.url("http://localhost:9000/users/" + USERNAME);

        CompletionStage<JsonNode> jsonPromise = request.setHeader(
                "Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .get()
                .thenApply(WSResponse::asJson);

        JsonNode jsonNode = jsonPromise.toCompletableFuture().get();
        assertEquals(USERNAME, jsonNode.get("username").textValue());

    }

    @Test
    public void userExist() throws Exception {
        WSRequest request = ws.url("http://localhost:9000/register/" + USERNAME);

        CompletionStage<Integer> jsonPromise = request
                .get()
                .thenApply(WSResponse::getStatus);

        int status = jsonPromise.toCompletableFuture().get();
        assertEquals(400, status);

    }

    @Test
    public void userNotExist() throws Exception {
        WSRequest request = ws.url("http://localhost:9000/register/nie_istnieje");

        CompletionStage<Integer> jsonPromise = request
                .get()
                .thenApply(WSResponse::getStatus);

        int status = jsonPromise.toCompletableFuture().get();
        assertEquals(200, status);

    }

    @Test
    public void getLoginUser() throws Exception {
        WSRequest request = ws.url("http://localhost:9000/me");

        CompletionStage<JsonNode> jsonPromise = request.setHeader(
                "Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .get()
                .thenApply(WSResponse::asJson);

        JsonNode jsonNode = jsonPromise.toCompletableFuture().get();
        assertEquals(USERNAME, jsonNode.get("username").textValue());

    }

    @Test
    public void createUser() throws Exception {

    }

    @Test
    public void register() throws Exception {

    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test
    public void deleteUser() throws Exception {

    }

    @Test
    public void createCountryAndAddPresidentAndAttackOtherCountry() throws Exception {

        WSRequest request = ws.url("http://localhost:9000/countries");

        ObjectNode node = Json.newObject();
        node.put("name", "Polska");
        node.put("currencyName", "PLN");
        node.put("parityRate", "10");

        CompletionStage<JsonNode> jsonPromise = request
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .post(node)
                .thenApply(WSResponse::asJson);

        JsonNode jsonNode = jsonPromise.toCompletableFuture().get();

        Long countryId = jsonNode.get("id").longValue();
        assertEquals("Polska", jsonNode.get("name").textValue());

        request = ws.url("http://localhost:9000/country/addPresident")
                .setQueryParameter("president", USERNAME)
                .setQueryParameter("country", String.valueOf(countryId));

        CompletionStage<Integer> jsonPromise2 = request
                .setHeader("Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .get()
                .thenApply(WSResponse::getStatus);

        int status = jsonPromise2.toCompletableFuture().get();
        assertEquals(204, status);

        request = ws.url("http://localhost:9000/countries");

        node = Json.newObject();
        node.put("name", "Niemcy");
        node.put("currencyName", "PLN");
        node.put("parityRate", "10");

        jsonPromise = request
                .setHeader("Content-Type", "application/json")
                .setHeader("Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .post(node)
                .thenApply(WSResponse::asJson);

        jsonNode = jsonPromise.toCompletableFuture().get();

        Long secondCountryId = jsonNode.get("id").longValue();
        assertEquals("Niemcy", jsonNode.get("name").textValue());

        request = ws.url("http://localhost:9000/country/callBattle")
                .setQueryParameter("aggressor", String.valueOf(countryId))
                .setQueryParameter("defender", String.valueOf(secondCountryId));

        CompletionStage<Integer> jsonPromise3 = request
                .setHeader("Authorization", "Basic " + Base64.encodeBase64String((user + ":" + token).getBytes()))
                .get()
                .thenApply(WSResponse::getStatus);

        assertEquals(200, (int)jsonPromise3.toCompletableFuture().get());

    }

}