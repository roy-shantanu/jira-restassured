import model.User;
import model.request.CreateProjectRequestBody;
import model.response.CreateIssueResponse;
import model.response.CreateProjectResponse;
import org.testng.annotations.Test;
import service.RestService;
import utils.ResourceLoader;
import utils.UserProvider;

import java.io.File;

import static utils.RequestBodyGenerator.getCreateProjectRequestBuilderWithRandomData;
import static utils.RequestBodyGenerator.getRandomIssueCreateBody;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 12:10 AM
 */
public class IssueCreationTests extends BaseTest {

    private RestService restService = new RestService();

    @Test
    public void issueCreationShouldSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectRequestBody requestBody = getCreateProjectRequestBuilderWithRandomData(user).build();

        CreateProjectResponse createProjectResponse = restService.createProject(user, requestBody)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateProjectResponse.class);

        restService.createIssue(user, getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())))
                .then()
                .statusCode(201);
    }

    @Test
    public void createIssueWithAttachmentShouldSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectRequestBody requestBody = getCreateProjectRequestBuilderWithRandomData(user).build();

        CreateProjectResponse createProjectResponse = restService.createProject(user, requestBody)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateProjectResponse.class);

        CreateIssueResponse createIssueResponse = restService
                .createIssue(user, getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())))
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        File attachment = new ResourceLoader().getResource("attachments/testAttachment.jpg");

        restService.addAttachmentToIssue(user, createIssueResponse.getId(), attachment)
                .then()
                .statusCode(200);

    }

    @Test
    public void deleteIssueShouldSSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectRequestBody requestBody = getCreateProjectRequestBuilderWithRandomData(user).build();

        CreateProjectResponse createProjectResponse = restService.createProject(user, requestBody)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateProjectResponse.class);

        CreateIssueResponse createIssueResponse = restService
                .createIssue(user, getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())))
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        restService.getIssue(user, createIssueResponse.getId())
                .then()
                .statusCode(200);

        restService.deleteIssue(user, createIssueResponse.getId())
                .then()
                .statusCode(204);

        restService.deleteProject(user, Integer.toString(createProjectResponse.getId()))
                .then()
                .statusCode(204);
    }

    @Test
    public void getAllIssuesShouldSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateProjectRequestBody requestBody = getCreateProjectRequestBuilderWithRandomData(user).build();

        CreateProjectResponse createProjectResponse = restService.createProject(user, requestBody)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateProjectResponse.class);

        CreateIssueResponse createIssueResponse1 = restService
                .createIssue(user, getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())))
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        CreateIssueResponse createIssueResponse2 = restService
                .createIssue(user, getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())))
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        restService.getIssuesForProject(user, Integer.toString(createProjectResponse.getId()))
                .then()
                .statusCode(200);

        restService.deleteIssue(user, createIssueResponse1.getId())
                .then()
                .statusCode(204);

        restService.deleteIssue(user, createIssueResponse2.getId())
                .then()
                .statusCode(204);

        restService.deleteProject(user, Integer.toString(createProjectResponse.getId()))
                .then()
                .statusCode(204);
    }
}
