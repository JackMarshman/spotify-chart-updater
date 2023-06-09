package spotifychartupdater.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Artist {

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;
}
