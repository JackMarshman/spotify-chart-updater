package spotifychartupdater.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Playlist {

    private boolean collaborative;
    private String description;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;
    private Followers followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Owner owner;

    @SerializedName("primary_color")
    private String primaryColour;

    @SerializedName("public")
    private boolean isPublic;

    @SerializedName("snapshot_id")
    private String snapshotId;

    private Tracks tracks;
}


