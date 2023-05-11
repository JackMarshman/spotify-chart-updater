package spotifychartupdater.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Album {

    @SerializedName("album_group")
    private String albumGroup;

    @SerializedName("album_type")
    private String albumType;
    private List<Artist> artists;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;
    private String href;
    private String id;
    private List<Image> images;

    @SerializedName("is_playable")
    private boolean isPlayable;
    private String name;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("release_date_precision")
    private String releaseDatePrecision;

    @SerializedName("total_tracks")
    private int totalTracks;
    private String type;
    private String uri;
}
