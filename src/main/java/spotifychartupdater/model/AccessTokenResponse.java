package spotifychartupdater.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class AccessTokenResponse {

    @SerializedName("access_token")
    public String accessToken;

    @SerializedName("token_type")
    public String tokenType;

    @SerializedName("expires_in")
    public int expiresIn;
}
