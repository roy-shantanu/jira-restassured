package model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Fields{

	@SerializedName("summary")
	private String summary;

	@SerializedName("issuetype")
	private Issuetype issuetype;

	@SerializedName("creator")
	private Creator creator;

	@SerializedName("created")
	private String created;

	@SerializedName("description")
	private String description;

	@SerializedName("project")
	private Project project;

	@SerializedName("reporter")
	private Reporter reporter;

	@SerializedName("priority")
	private Priority priority;

	@SerializedName("attachment")
	private List<Object> attachment;

	@SerializedName("comment")
	private Comment comment;

	@SerializedName("assignee")
	private Assignee assignee;

	@SerializedName("updated")
	private String updated;

	@SerializedName("status")
	private Status status;
}