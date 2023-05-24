package spotifychartupdater.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TrackItem {

    @SerializedName("added_at")
    private String addedAt;

    @SerializedName("added_by")
    private AddedBy addedBy;

    @SerializedName("is_local")
    private boolean isLocal;

    @SerializedName("primary_color")
    private String primaryColor;

    private Track track;
}
