package service;

import io.restassured.response.Response;
import model.User;
import model.request.CreateIssueRequestBody;
import model.request.CreateProjectRequestBody;
import model.response.CreateProjectResponse;
import utils.ProjectCleanupHelper;

import java.io.File;

/**
 * User: Shantanu Roy
 * Date: 22-Jun-20
 * Time: 2:41 PM
 */
public class RestServiceProxy implements RestService {

    private final RestService restService = new RestServiceImpl();

    public Response createProject(User user, CreateProjectRequestBody requestBody) {
        Response response = null;
        try {
            response = this.restService.createProject(user, requestBody);
        } finally {
            if (response != null && response.statusCode() == 201) {
                ProjectCleanupHelper.getInstance()
                        .addProjectForCleanUp(response
                                .as(CreateProjectResponse.class)
                                .getKey());
            }
        }
        return response;
    }

    public Response deleteProject(User user, String projectIdOrKey) {
        Response response = null;
        try {
            response = this.restService.deleteProject(user, projectIdOrKey);
        } finally {
            if (response != null && response.statusCode() == 204) {
                ProjectCleanupHelper.getInstance()
                        .projectDeleted(projectIdOrKey);
            }
        }
        return response;
    }

    public Response createIssue(User user, CreateIssueRequestBody requestBody) {
        return this.restService.createIssue(user, requestBody);
    }

    public Response addAttachmentToIssue(User user, String issueIdOrKey, File attachment) {
        return this.restService.addAttachmentToIssue(user, issueIdOrKey, attachment);
    }

    public Response deleteIssue(User user, String issueIdOrKey) {
        return this.restService.deleteIssue(user, issueIdOrKey);
    }

    public Response getIssue(User user, String issueIdOrKey) {
        return this.restService.getIssue(user, issueIdOrKey);
    }

    public Response getIssuesForProject(User user, String projectKey) {
        return this.restService.getIssuesForProject(user, projectKey);
    }
}
