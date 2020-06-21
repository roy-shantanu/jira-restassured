package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Project{

	@SerializedName("avatarUrls")
	private AvatarUrls avatarUrls;

	@SerializedName("name")
	private String name;

	@SerializedName("self")
	private String self;

	@SerializedName("id")
	private String id;

	@SerializedName("projectTypeKey")
	private String projectTypeKey;

	@SerializedName("key")
	private String key;
}