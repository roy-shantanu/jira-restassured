package model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Comment{

	@SerializedName("total")
	private int total;

	@SerializedName("comments")
	private List<Object> comments;

	@SerializedName("maxResults")
	private int maxResults;

	@SerializedName("startAt")
	private int startAt;
}