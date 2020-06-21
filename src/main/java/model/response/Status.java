package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Status{

	@SerializedName("name")
	private String name;

	@SerializedName("self")
	private String self;

	@SerializedName("description")
	private String description;

	@SerializedName("iconUrl")
	private String iconUrl;

	@SerializedName("id")
	private String id;

	@SerializedName("statusCategory")
	private StatusCategory statusCategory;
}