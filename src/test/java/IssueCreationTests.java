import customassertions.GetIssueAssert;
import model.User;
import model.request.CreateIssueRequestBody;
import model.response.*;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import service.RestService;
import utils.ResourceLoader;
import utils.UserProvider;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static utils.RequestBodyGenerator.getCreateProjectRequestBuilderWithRandomData;
import static utils.RequestBodyGenerator.getRandomIssueCreateBody;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 12:10 AM
 */
public class IssueCreationTests extends BaseTest {

    private RestService restService = new RestService();
    private final String defaultProjectId = getApplicationProperties().getProperty("default.project.id");

    @Test(groups = "schema")
    public void issueCreationSchema_ShouldSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        File schema = new ResourceLoader().getResource("schema/create_issue.json");

        restService.createIssue(user, getRandomIssueCreateBody(user, defaultProjectId).build())
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body(matchesJsonSchema(schema));
    }

    @Test(groups = "schema")
    public void addAttachmentToIssueSchema_ShouldMatch() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");
        File schema = new ResourceLoader().getResource("schema/add_attachment.json");

        CreateIssueResponse createIssueResponse = restService
                .createIssue(user, getRandomIssueCreateBody(user, defaultProjectId).build())
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        GetIssueResponse issueBefore = restService.getIssue(user, createIssueResponse.getId())
                .then()
                .statusCode(200)
                .extract()

                .as(GetIssueResponse.class);

        assertThat(issueBefore.getFields().getAttachment().size()).isEqualTo(0);

        File attachment = new ResourceLoader().getResource("attachments/testAttachment.jpg");

        restService.addAttachmentToIssue(user, createIssueResponse.getId(), attachment)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(matchesJsonSchema(schema));

        GetIssueResponse issueAfter = restService.getIssue(user, createIssueResponse.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(GetIssueResponse.class);

        assertThat(issueAfter.getFields().getAttachment().size()).isEqualTo(1);
    }

    @Test
    public void deleteIssue_ShouldSSucceed() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        CreateIssueResponse createIssueResponse = restService
                .createIssue(user, getRandomIssueCreateBody(user, defaultProjectId).build())
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        restService.deleteIssue(user, createIssueResponse.getId())
                .then()
                .statusCode(204);
    }

    @Test(groups = "schema")
    public void getIssueSchema_ShouldMatch() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");
        File schema = new ResourceLoader().getResource("schema/get_issue.json");

        CreateIssueResponse createIssueResponse = restService
                .createIssue(user, getRandomIssueCreateBody(user, defaultProjectId).build())
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        restService.getIssue(user, createIssueResponse.getId())
                .then()
                .statusCode(200)
                .and()
                .body(matchesJsonSchema(schema));
    }

    @Test(groups = "schema")
    public void getAllIssuesSchema_ShouldMatch() {
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");
        File schema = new ResourceLoader().getResource("schema/get_all_issues.json");

        restService
                .createIssue(user, getRandomIssueCreateBody(user, defaultProjectId).build())
                .then()
                .statusCode(201);

        restService.getIssuesForProject(user, defaultProjectId)
                .then()
                .statusCode(200)
                .and()
                .body(matchesJsonSchema(schema));
    }

    @Test
    public void adminUserShouldBeAbleTo_CreateIssueOnBehalf_OfOtherUsers() {
        User admin = UserProvider.getInstance().getAuthenticatedUser("admin");
        User nonAdmin = UserProvider.getInstance().getUser("nonAdmin");

        CreateIssueRequestBody requestBody = getRandomIssueCreateBody(admin, defaultProjectId)
                .reporter(nonAdmin.getUsername())
                .assignee(nonAdmin.getUsername())
                .build();

        CreateIssueResponse createIssueResponse = restService
                .createIssue(admin, requestBody)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        GetIssueResponse getIssueResponse = restService.getIssue(admin, createIssueResponse.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(GetIssueResponse.class);

        /*
        Without custom assertion, multiple assertion would look like this see assignmentTestCase() for
        custom assertion example
        */
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getIssueResponse.getFields().getCreator().getName()).isEqualTo(admin.getUsername());
        softly.assertThat(getIssueResponse.getFields().getReporter().getName()).isEqualTo(nonAdmin.getUsername());
        softly.assertThat(getIssueResponse.getFields().getAssignee().getName()).isEqualTo(nonAdmin.getUsername());
        softly.assertAll();
    }

    @Test
    public void nonAdminUserShouldNotBeAbleTo_CreateIssueOnBehalf_OfOtherUsers() {
        User nonAdmin1 = UserProvider.getInstance().getAuthenticatedUser("nonAdmin");
        User nonAdmin2 = UserProvider.getInstance().getUser("nonAdmin2");

        CreateIssueRequestBody requestBody = getRandomIssueCreateBody(nonAdmin1, defaultProjectId)
                .reporter(nonAdmin2.getUsername())
                .assignee(nonAdmin2.getUsername())
                .build();

        restService
                .createIssue(nonAdmin1, requestBody)
                .then()
                .statusCode(400)
                .and()
                .body("errors.reporter", equalTo("Field 'reporter' cannot be set. It is not on the " +
                        "appropriate screen, or unknown."));
    }

    @Test
    public void nonAdminUser_ShouldNotBeAbleTo_DeleteIssue() {
        User nonAdminUser = UserProvider.getInstance().getAuthenticatedUser("nonAdmin");

        CreateIssueResponse createIssueResponse = restService
                .createIssue(nonAdminUser, getRandomIssueCreateBody(nonAdminUser, defaultProjectId).build())
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        ErrorResponse errorResponse = restService.deleteIssue(nonAdminUser, createIssueResponse.getId())
                .then()
                .statusCode(403)
                .and()
                .extract()
                .as(ErrorResponse.class);

        assertThat(errorResponse.getErrorMessages().get(0)).isEqualTo("You do not have permission to delete issues" +
                " in this project.");
    }

    @Test
    public void assignmentTestCase() {
        //the admin user logs in to the application
        User user = UserProvider.getInstance().getAuthenticatedUser("admin");

        //creating a project
        CreateProjectResponse createProjectResponse = restService.createProject(user, getCreateProjectRequestBuilderWithRandomData(user).build())
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .as(CreateProjectResponse.class);

        //create first issue
        CreateIssueRequestBody firstIssueRequestsBody = getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())).build();

        CreateIssueResponse createFirstIssueResponse = restService
                .createIssue(user, firstIssueRequestsBody)
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class);

        //adding attachment
        File attachment = new ResourceLoader().getResource("attachments/testAttachment.jpg");

        restService.addAttachmentToIssue(user, createFirstIssueResponse.getId(), attachment)
                .then()
                .statusCode(200);

        //creating another issue
        String issueTwoId = restService
                .createIssue(user, getRandomIssueCreateBody(user, Integer.toString(createProjectResponse.getId())).build())
                .then()
                .statusCode(201)
                .extract()
                .as(CreateIssueResponse.class)
                .getId();

        //deleting second issue
        restService.deleteIssue(user, issueTwoId)
                .then()
                .statusCode(204);

        //showing details of first issue
        GetIssueResponse getFirstIssueResponse = restService.getIssue(user, createFirstIssueResponse.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(GetIssueResponse.class);

        /*
        Using custom assertion to get a fluent assertion see
        adminUserShouldBeAbleTo_CreateIssueOnBehalf_OfOtherUsers()
        for assertion without custom assertion, also see GetIssueAssert
        for the implementation of the custom assertion
        */
        GetIssueAssert.assertThat(getFirstIssueResponse)
                .hasId(createFirstIssueResponse.getId())
                .hasKey(createFirstIssueResponse.getKey())
                .hasSelf(createFirstIssueResponse.getSelf())
                .hasDescription(firstIssueRequestsBody.getFields().getDescription())
                .hasSummary(firstIssueRequestsBody.getFields().getSummary())
                .hasAssignee(user)
                .hasCreator(user)
                .hasReporter(user);


        //showing all issues from the project
        GetIssuesForProjectResponse getAllIssues = restService.getIssuesForProject(user, createProjectResponse.getKey())
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(GetIssuesForProjectResponse.class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getAllIssues.getIssues().size()).isEqualTo(1);
        softly.assertThat(getAllIssues.getIssues().get(0).getKey()).isEqualTo(getFirstIssueResponse.getKey());
        softly.assertAll();
    }
}
