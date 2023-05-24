package spotifychartupdater.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Track {

    private Album album;
    private List<Artist> artists;

    @SerializedName("available_markets")
    private List<String> availableMarkets;

    @SerializedName("disc_number")
    private String discNumber;

    @SerializedName("duration_ms")
    private int durationMs;

    private boolean episode;
    private boolean explicit;

    @SerializedName("external_ids")
    private ExternalIds externalIds;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    private String href;
    private String id;

    @SerializedName("is_local")
    private boolean isLocal;

    private String name;
    private int popularity;
}
