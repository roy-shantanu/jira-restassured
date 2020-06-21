package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AvatarUrls{

	@SerializedName("48x48")
	private String jsonMember48x48;

	@SerializedName("24x24")
	private String jsonMember24x24;

	@SerializedName("16x16")
	private String jsonMember16x16;

	@SerializedName("32x32")
	private String jsonMember32x32;
}