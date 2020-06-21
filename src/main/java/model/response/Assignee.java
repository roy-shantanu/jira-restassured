package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Assignee{

	@SerializedName("emailAddress")
	private String emailAddress;

	@SerializedName("avatarUrls")
	private AvatarUrls avatarUrls;

	@SerializedName("displayName")
	private String displayName;

	@SerializedName("name")
	private String name;

	@SerializedName("self")
	private String self;

	@SerializedName("active")
	private boolean active;

	@SerializedName("timeZone")
	private String timeZone;

	@SerializedName("key")
	private String key;
}