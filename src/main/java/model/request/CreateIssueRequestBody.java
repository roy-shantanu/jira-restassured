package model.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateIssueRequestBody {

    @SerializedName("fields")
    private Fields fields;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String summary;
        private IssueType issuetype;
        private Project project;
        private String description;
        private Reporter reporter;
        private Assignee assignee;
        private Priority priority;

        Builder() {
        }

        public Builder summary(String summary) {
            this.summary = summary;
            return this;
        }

        public Builder issueType(String issueTypeId) {
            this.issuetype = new IssueType(issueTypeId);
            return this;
        }

        public Builder project(String projectId) {
            this.project = new Project(projectId);
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder reporter(String reporterName) {
            this.reporter = new Reporter(reporterName);
            return this;
        }

        public Builder assignee(String assigneeName) {
            this.assignee = new Assignee(assigneeName);
            return this;
        }

        public Builder priority(String priorityId) {
            this.priority = new Priority(priorityId);
            return this;
        }

        public CreateIssueRequestBody build() {
            return new CreateIssueRequestBody(new Fields(summary, issuetype, project, description, reporter, assignee, priority));
        }
    }
}