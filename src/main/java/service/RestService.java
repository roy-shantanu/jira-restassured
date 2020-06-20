package service;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.User;
import model.request.CreateIssueRequestBody;
import model.request.CreateProjectRequestBody;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 9:14 PM
 */
public class RestService extends BaseService {

    @Override
    protected String apiName() {
        return "api";
    }

    @Override
    protected String apiVersion() {
        return "2";
    }

    private RequestSpecification authenticatedUserSpec(User user) {
        return baseSpecBuilder()
                .addCookie("JSESSIONID", user.getSessionId())
                .build();
    }

    public Response createProject(User user, CreateProjectRequestBody requestBody) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/project");
    }

    public Response deleteProject(User user, String projectIdOrKey) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType(JSON)
                .pathParam("projectIdOrKey", projectIdOrKey)
                .when()
                .delete("/project/{projectIdOrKey}");
    }

    public Response createIssue(User user, CreateIssueRequestBody requestBody) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType(JSON)
                .body(requestBody)
                .when()
                .post("/issue");
    }

    public Response addAttachmentToIssue(User user, String issueIdOrKey, File attachment) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType("multipart/form-data")
                .header("X-Atlassian-Token", "no-check")
                .pathParam("issueIdOrKey", issueIdOrKey)
                .multiPart(attachment)
                .when()
                .post("issue/{issueIdOrKey}/attachments");
    }

    public Response deleteIssue(User user, String issueIdOrKey) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType(JSON)
                .pathParam("issueIdOrKey", issueIdOrKey)
                .when()
                .delete("/issue/{issueIdOrKey}");
    }

    public Response getIssue(User user, String issueIdOrKey) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType(JSON)
                .pathParam("issueIdOrKey", issueIdOrKey)
                .when()
                .get("/issue/{issueIdOrKey}");
    }

    public Response getIssuesForProject(User user, String projectKey) {
        return given()
                .spec(authenticatedUserSpec(user))
                .contentType(JSON)
                .queryParam("jql=project", projectKey)
                .when()
                .get("/search");
    }
}
