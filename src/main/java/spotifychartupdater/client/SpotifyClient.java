package spotifychartupdater.client;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import spotifychartupdater.config.SpotifyChartUpdaterProperties;
import spotifychartupdater.model.AccessTokenResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpotifyClient {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final SpotifyChartUpdaterProperties spotifyChartUpdaterProperties;
    private String accessToken;
    private static final String ACCESS_TOKEN_GRANT_TYPE = "client_credentials";

    @PostConstruct
    public void generateAccessToken() {
        log.info("Requesting access token for new session.");
        this.accessToken = retrieveAccessToken();
    }

    public ResponseEntity<String> retrieveDataFromSpotify(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, buildGenericRequest(), String.class);
    }

    private String retrieveAccessToken() {
        HttpEntity<MultiValueMap<String, String>> request = buildAccessTokenRequest();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, String.class);
        AccessTokenResponse response = gson.fromJson(responseEntity.getBody(), AccessTokenResponse.class);

        return response.getAccessToken();
    }

    //TODO - can this be done via the RestTemplate bean??
    private HttpEntity<String> buildGenericRequest() {
        return new HttpEntity<>(createGenericRequestHeaders());
    }

    private HttpHeaders createGenericRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));

        return headers;
    }

    private HttpEntity<MultiValueMap<String, String>> buildAccessTokenRequest() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", ACCESS_TOKEN_GRANT_TYPE);
        requestBody.add("client_id", spotifyChartUpdaterProperties.getClientId());
        requestBody.add("client_secret", spotifyChartUpdaterProperties.getClientSecret());

        HttpHeaders requestHeaders = createAccessTokenRequestHeaders();

        return new HttpEntity<>(requestBody, requestHeaders);
    }

    private HttpHeaders createAccessTokenRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return headers;
    }
}
