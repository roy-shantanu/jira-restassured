package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetIssueResponse{

	@SerializedName("expand")
	private String expand;

	@SerializedName("self")
	private String self;

	@SerializedName("id")
	private String id;

	@SerializedName("fields")
	private Fields fields;

	@SerializedName("key")
	private String key;
}