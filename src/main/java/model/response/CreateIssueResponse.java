package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateIssueResponse{

	@SerializedName("self")
	private String self;

	@SerializedName("id")
	private String id;

	@SerializedName("key")
	private String key;
}