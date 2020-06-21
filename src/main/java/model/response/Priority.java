package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Priority{

	@SerializedName("name")
	private String name;

	@SerializedName("self")
	private String self;

	@SerializedName("iconUrl")
	private String iconUrl;

	@SerializedName("id")
	private String id;
}