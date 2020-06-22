package service;

import io.restassured.response.Response;
import model.User;

/**
 * User: Shantanu Roy
 * Date: 18-Jun-20
 * Time: 4:37 PM
 */
public interface AuthenticationService {

    Response authenticateUser(User user);

}
