import model.User;
import model.response.AuthenticationResponse;
import org.testng.annotations.Test;
import service.AuthenticationService;
import service.RestService;
import utils.ResourceLoader;
import utils.UserProvider;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static utils.RequestBodyGenerator.getCreateProjectRequestBuilderWithRandomData;


/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 2:03 PM
 */
public class AuthenticationTests extends BaseTest {

    private AuthenticationService authenticationService = new AuthenticationService();
    private RestService restService = new RestService();

    @Test(groups = "schema")
    public void authenticateUser_SchemaShouldMatch() {
        User user = UserProvider.getInstance().getUser("admin");

        File schema = new ResourceLoader().getResource("schema/authenticate_user.json");

        user.setSessionId(null);

        authenticationService.authenticateUser(user)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(matchesJsonSchema(schema));
    }

    @Test
    public void alreadyAuthenticatedUser_ShouldGetNewSessionId_AfterReAuthentication() {
        User user = UserProvider.getInstance().getUser("admin");

        user.setSessionId(null);

        AuthenticationResponse authenticationResponse1 = authenticationService.authenticateUser(user)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(AuthenticationResponse.class);

        AuthenticationResponse authenticationResponse2 = authenticationService.authenticateUser(user)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(AuthenticationResponse.class);

        assertThat(authenticationResponse1.getSession().getValue())
                .isNotEqualTo(authenticationResponse2.getSession().getValue());
    }

    @Test
    public void adminShouldNotBeAble_ToCreateProject_WithoutAuthenticating() {
        User admin = UserProvider.getInstance().getUser("admin");

        admin.setSessionId(null);

        restService.createProject(admin, getCreateProjectRequestBuilderWithRandomData(admin).build())
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void newAuthenticatedSession_ShouldNotExpireOldSessions() {
        User user = UserProvider.getInstance().getUser("admin");

        user.setSessionId(null);

        AuthenticationResponse authenticationResponse1 = authenticationService.authenticateUser(user)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(AuthenticationResponse.class);

        authenticationService.authenticateUser(user)
                .then()
                .assertThat();

        user.setSessionId(authenticationResponse1.getSession().getValue());

        restService.createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test //This fails, and this should not pass
    public void userShouldNotBeAbleToUse_OtherUsersSessionId() {
        User admin1 = UserProvider.getInstance().getUser("admin");
        User admin2 = UserProvider.getInstance().getUser("admin2");

        admin1.setSessionId(null);
        admin2.setSessionId(null);

        AuthenticationResponse authenticationResponse2 = authenticationService.authenticateUser(admin2)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(AuthenticationResponse.class);

        admin1.setSessionId(authenticationResponse2.getSession().getValue());

        restService.createProject(admin1, getCreateProjectRequestBuilderWithRandomData(admin1).build())
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void userShouldNotBeAbleToUse_InvalidSessionId() {
        User user = UserProvider.getInstance().getUser("admin");

        user.setSessionId("randomSessionId");

        restService.createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(401);
    }
}
