package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Session{

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private String value;
}