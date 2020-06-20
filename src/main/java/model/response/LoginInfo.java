package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInfo{

	@SerializedName("failedLoginCount")
	private int failedLoginCount;

	@SerializedName("lastFailedLoginTime")
	private String lastFailedLoginTime;

	@SerializedName("loginCount")
	private int loginCount;

	@SerializedName("previousLoginTime")
	private String previousLoginTime;
}