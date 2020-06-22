package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.extern.apachecommons.CommonsLog;
import model.User;
import model.response.AuthenticationResponse;
import service.AuthenticationService;
import service.Services;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 10:31 PM
 */
@CommonsLog
public class UserProvider {

    private static final UserProvider instance = new UserProvider();
    private AuthenticationService authenticationService = Services.getAuthService();
    private Map<String, User> users;

    private UserProvider() {
        users = new HashMap<>();
        loadUsers();
    }

    public static UserProvider getInstance() {
        return instance;
    }

    public User getUser(String storedUserName) {
        return users
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(storedUserName))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("User not found"));
    }

    public User getAuthenticatedUser(String storedUserName) {
        User user = getUser(storedUserName);

        if (user.getSessionId() == null) {
            return authenticateUser(user);
        }
        return user;
    }

    private User authenticateUser(User user) {
        AuthenticationResponse authResponse = authenticationService
                .authenticateUser(user)
                .then()
                .statusCode(200)
                .extract()
                .as(AuthenticationResponse.class);
        user.setSessionId(authResponse.getSession().getValue());
        return user;
    }

    private void loadUsers() {
        URL userFilePath = ClassLoader.getSystemClassLoader().getResource("users.json");
        try (JsonReader reader = new JsonReader(new FileReader(Objects.requireNonNull(userFilePath).getPath()))) {
            this.users = new Gson().fromJson(reader,
                    new TypeToken<HashMap<String, User>>() {
                    }.getType());
        } catch (IOException e) {
            log.error(e);
        }
    }
}
