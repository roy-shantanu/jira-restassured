package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StatusCategory{

	@SerializedName("colorName")
	private String colorName;

	@SerializedName("name")
	private String name;

	@SerializedName("self")
	private String self;

	@SerializedName("id")
	private int id;

	@SerializedName("key")
	private String key;
}