package spotifychartupdater.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Owner {

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    private String href;
    private String id;
    private String type;
    private String uri;
}
