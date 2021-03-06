package utils;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import model.User;
import model.request.CreateIssueRequestBody;
import model.request.CreateProjectRequestBody;

import static model.AssigneeType.PROJECT_LEAD;
import static model.ProjectTemplate.SOFTWARE_SCRUM;
import static model.ProjectType.SOFTWARE;

/**
 * User: Shantanu Roy
 * Date: 20-Jun-20
 * Time: 1:04 PM
 */
public class RequestBodyGenerator {

    private RequestBodyGenerator() {
    }

    public static CreateIssueRequestBody.Builder getRandomIssueCreateBody(User user, String projectId) {
        return CreateIssueRequestBody.builder()
                .summary(LoremIpsum.getInstance().getWords(5))
                .issueType("10002")
                .project(projectId)
                .description(LoremIpsum.getInstance().getWords(5))
                .assignee(user.getUsername())
                .priority("3");
    }

    public static CreateProjectRequestBody.CreateProjectRequestBodyBuilder getCreateProjectRequestBuilderWithRandomData(User user) {
        Lorem lorem = LoremIpsum.getInstance();
        String key = lorem.getWords(1).toUpperCase();
        return CreateProjectRequestBody.builder()
                .key(key.length() > 10 ? key.substring(0, 9) : key)
                .name(lorem.getWords(1).toUpperCase())
                .projectTypeKey(SOFTWARE.getProjectTypeKey())
                .projectTemplateKey(SOFTWARE_SCRUM.getProjectTemplateKey())
                .description(lorem.getWords(5))
                .lead(user.getUsername())
                .assigneeType(PROJECT_LEAD.getTypeName())
                .avatarId(10200)
                .notificationScheme(10000)
                .permissionScheme(10000);
    }
}
