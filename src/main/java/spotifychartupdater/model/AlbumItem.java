package spotifychartupdater.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class AlbumItem {

    @SerializedName("album_group")
    private String album_group;

    @SerializedName("album_type")
    private String album_type;
    private List<Artist> artists;

    @SerializedName("external_urls")
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<Image> images;

    @SerializedName("is_playable")
    private boolean is_playable;
    private String name;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("release_date_precision")
    private String release_date_precision;

    @SerializedName("total_tracks")
    private int total_tracks;
    private String type;
    private String uri;
}
