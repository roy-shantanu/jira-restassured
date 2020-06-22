package customassertions;

import model.User;
import model.response.GetIssueResponse;
import org.assertj.core.api.AbstractAssert;

/**
 * User: Shantanu Roy
 * Date: 21-Jun-20
 * Time: 9:52 PM
 */
public class GetIssueAssert extends AbstractAssert<GetIssueAssert, GetIssueResponse> {

    public GetIssueAssert(GetIssueResponse getIssueResponse) {
        super(getIssueResponse, GetIssueAssert.class);
    }

    public static GetIssueAssert assertThat(GetIssueResponse actual) {
        return new GetIssueAssert(actual);
    }

    public GetIssueAssert hasId(String id) {
        isNotNull();
        if (!actual.getId().equals(id)) {
            failWithMessage("Expected id of " + id + " didn't match the id "
                    + actual.getId() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasKey(String key) {
        isNotNull();
        if (!actual.getKey().equals(key)) {
            failWithMessage("Expected key of " + key + " didn't match the key "
                    + actual.getKey() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasSelf(String self) {
        isNotNull();
        if (!actual.getSelf().equals(self)) {
            failWithMessage("Expected self of " + self + " didn't match the self "
                    + actual.getSelf() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasDescription(String description) {
        isNotNull();
        if (!actual.getFields().getDescription().equals(description)) {
            failWithMessage("Expected description of " + description + " didn't match the description "
                    + actual.getFields().getDescription() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasSummary(String summary) {
        isNotNull();
        if (!actual.getFields().getSummary().equals(summary)) {
            failWithMessage("Expected summary of " + summary + " didn't match the summary "
                    + actual.getFields().getSummary() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasAssignee(User assignee) {
        isNotNull();
        if (!actual.getFields().getAssignee().getName().equals(assignee.getUsername())) {
            failWithMessage("Expected assignee of " + assignee.getUsername() + " didn't match the assignee "
                    + actual.getFields().getAssignee().getName() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasCreator(User creator) {
        isNotNull();
        if (!actual.getFields().getCreator().getName().equals(creator.getUsername())) {
            failWithMessage("Expected creator of " + creator.getUsername() + " didn't match the creator "
                    + actual.getFields().getCreator().getName() + " in api response");
        }
        return this;
    }

    public GetIssueAssert hasReporter(User reporter) {
        isNotNull();
        if (!actual.getFields().getReporter().getName().equals(reporter.getUsername())) {
            failWithMessage("Expected reporter of " + reporter.getUsername() + " didn't match the reporter "
                    + actual.getFields().getReporter().getName() + " in api response");
        }
        return this;
    }


}
