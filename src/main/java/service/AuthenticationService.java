package service;

import io.restassured.response.Response;
import model.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

/**
 * User: Shantanu Roy
 * Date: 18-Jun-20
 * Time: 4:37 PM
 */
public class AuthenticationService extends BaseService {

    @Override
    protected String apiName() {
        return "auth";
    }

    @Override
    protected String apiVersion() {
        return "1";
    }

    public Response authenticateUser(User user){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", user.getUsername());
        requestBody.put("password", user.getPassword());
        return given()
                .spec(baseSpecBuilder().build())
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/session");
    }
}
