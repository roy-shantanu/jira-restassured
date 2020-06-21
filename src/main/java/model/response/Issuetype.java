package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Issuetype{

	@SerializedName("name")
	private String name;

	@SerializedName("self")
	private String self;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("iconUrl")
	private String iconUrl;

	@SerializedName("subtask")
	private boolean subTask;
}