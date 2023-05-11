package spotifychartupdater.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("spotify")
public class SpotifyChartUpdaterProperties {

    public String clientId;
    public String clientSecret;
}
