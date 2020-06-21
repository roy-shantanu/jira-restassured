package model.response;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetIssuesForProjectResponse {

    @SerializedName("expand")
    private String expand;

    @SerializedName("total")
    private int total;

    @SerializedName("maxResults")
    private int maxResults;

    @SerializedName("issues")
    private List<GetIssueResponse> issues;

    @SerializedName("startAt")
    private int startAt;
}