package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse{

	@SerializedName("session")
	private Session session;

	@SerializedName("loginInfo")
	private LoginInfo loginInfo;
}