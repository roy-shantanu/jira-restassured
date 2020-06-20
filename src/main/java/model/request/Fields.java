package model.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class Fields {

    @SerializedName("summary")
    private String summary;

    @SerializedName("issuetype")
    private IssueType issueType;

    @SerializedName("project")
    private Project project;

    @SerializedName("description")
    private String description;

    @SerializedName("reporter")
    private Reporter reporter;

    @SerializedName("assignee")
    private Assignee assignee;

    @SerializedName("priority")
    private Priority priority;
}