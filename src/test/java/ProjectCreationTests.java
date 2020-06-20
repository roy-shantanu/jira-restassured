import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import model.User;
import model.request.CreateProjectRequestBody;
import model.response.CreateProjectResponse;
import model.response.ErrorResponse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import service.RestService;
import utils.ResourceLoader;
import utils.UserProvider;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.RequestBodyGenerator.getCreateProjectRequestBuilderWithRandomData;

/**
 * User: Shantanu Roy
 * Date: 18-Jun-20
 * Time: 4:31 PM
 */
public class ProjectCreationTests extends BaseTest {

    private RestService restService = new RestService();

    @Test(groups = "schema")
    public void createProjectSchema_ShouldMatch() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        File schema = new ResourceLoader().getResource("schema/create_project.json");

        restService.createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body(matchesJsonSchema(schema));
    }

    @Test
    public void adminUser_ShouldBeAble_ToCreateProject() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        restService.createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    public void nonAdminUser_ShouldNotBeAble_ToCreateProject() {
        User user = UserProvider.getInstance().getAuthenticatedUser("nonAdmin");

        ErrorResponse error = restService
                .createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(403)
                .and()
                .extract()
                .as(ErrorResponse.class);

        assertThat(error.getErrorMessages().get(0))
                .isEqualTo("You must have global administrator rights in order to modify projects.");
    }

    @Test
    public void projectDeletion_ByAdminUser_ShouldSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectResponse response = restService
                .createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .as(CreateProjectResponse.class);

        restService.deleteProject(user, Integer.toString(response.getId()))
                .then()
                .assertThat()
                .statusCode(204);
    }

    @Test
    public void projectDeletion_ByAnotherAdmin_ShouldSucceed() {
        User adminA = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectResponse response = restService
                .createProject(adminA, getCreateProjectRequestBuilderWithRandomData(adminA).build())
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .as(CreateProjectResponse.class);

        User adminB = UserProvider.getInstance().getAuthenticatedUser("admin2");

        restService.deleteProject(adminB, Integer.toString(response.getId()))
                .then()
                .assertThat()
                .statusCode(204);
    }

    @Test
    public void projectDeletion_ByNonAdmin_ShouldFail() {
        User admin = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectResponse response = restService
                .createProject(admin, getCreateProjectRequestBuilderWithRandomData(admin).build())
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .as(CreateProjectResponse.class);

        User nonAdmin = UserProvider.getInstance().getAuthenticatedUser("nonAdmin");

        ErrorResponse error = restService.deleteProject(nonAdmin, Integer.toString(response.getId()))
                .then()
                .assertThat()
                .statusCode(403)
                .and()
                .extract()
                .as(ErrorResponse.class);

        assertThat(error.getErrorMessages().get(0))
                .isEqualTo("You cannot delete this project.");
    }

    @Test(dataProvider = "invalidDataSet")
    public void projectCreationWithInvalidDataShouldFail(User user, CreateProjectRequestBody requestBody,
                                                         String errorKey, String message) {
        restService.createProject(user, requestBody)
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("errors." + errorKey, equalTo(message));
    }

    @DataProvider(name = "invalidDataSet")
    public Object[][] invalidDataSetProvider() {
        User admin = UserProvider.getInstance().getAuthenticatedUser("admin");
        Lorem lorem = LoremIpsum.getInstance();
        return new Object[][]{
                {admin,
                        getCreateProjectRequestBuilderWithRandomData(admin)
                                .key(null)
                                .build(),
                        "projectKey",
                        "Project keys must start with an uppercase letter, followed by one or more uppercase " +
                                "alphanumeric characters."
                },
                {admin,
                        getCreateProjectRequestBuilderWithRandomData(admin)
                                .key(lorem.getWords(1)
                                        .toLowerCase())
                                .build(),
                        "projectKey",
                        "Project keys must start with an uppercase letter, followed by one or more uppercase " +
                                "alphanumeric characters."
                },
                {admin,
                        getCreateProjectRequestBuilderWithRandomData(admin)
                                .name(null)
                                .build(),
                        "projectName",
                        "You must specify a valid project name."
                }
        };
    }

}
