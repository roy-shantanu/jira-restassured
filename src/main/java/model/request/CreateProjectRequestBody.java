package model.request;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;

@Builder
public class CreateProjectRequestBody{

	@SerializedName("projectTemplateKey")
	private String projectTemplateKey;

	@SerializedName("avatarId")
	private int avatarId;

	@SerializedName("notificationScheme")
	private int notificationScheme;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("permissionScheme")
	private int permissionScheme;

	@SerializedName("assigneeType")
	private String assigneeType;

	@SerializedName("projectTypeKey")
	private String projectTypeKey;

	@SerializedName("key")
	private String key;

	@SerializedName("lead")
	private String lead;
}