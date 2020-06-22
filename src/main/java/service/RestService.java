package service;

import io.restassured.response.Response;
import model.User;
import model.request.CreateIssueRequestBody;
import model.request.CreateProjectRequestBody;

import java.io.File;

/**
 * User: Shantanu Roy
 * Date: 19-Jun-20
 * Time: 9:14 PM
 */
public interface RestService {

    Response createProject(User user, CreateProjectRequestBody requestBody);

    Response deleteProject(User user, String projectIdOrKey);

    Response createIssue(User user, CreateIssueRequestBody requestBody);

    Response addAttachmentToIssue(User user, String issueIdOrKey, File attachment);

    Response deleteIssue(User user, String issueIdOrKey);

    Response getIssue(User user, String issueIdOrKey);

     Response getIssuesForProject(User user, String projectKey);
}
