package model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse{

	@SerializedName("errorMessages")
	private List<String> errorMessages;

	@SerializedName("errors")
	private Errors errors;
}