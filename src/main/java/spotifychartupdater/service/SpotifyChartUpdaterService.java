package spotifychartupdater.service;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import spotifychartupdater.config.SpotifyChartUpdaterProperties;
import spotifychartupdater.model.AccessTokenResponse;
import spotifychartupdater.model.AlbumQueryResult;

import java.util.Arrays;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotifyChartUpdaterService {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final SpotifyChartUpdaterProperties spotifyChartUpdaterProperties;
    private String accessToken;
    private static final String ACCESS_TOKEN_GRANT_TYPE = "client_credentials";

    @PostConstruct
    public void generateAccessToken() {
        log.info("Generating access token.");
        this.accessToken = retrieveAccessToken();
    }

    private String retrieveAccessToken() {
        HttpEntity<MultiValueMap<String, String>> request = buildAccessTokenRequest();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, String.class);
        AccessTokenResponse response = gson.fromJson(responseEntity.getBody(), AccessTokenResponse.class);

        return response.getAccessToken();
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

    public AlbumQueryResult retrieveAlbumByArtist(String artistName, String albumName) {
        log.info("Querying Spotify for album: {} by artist: {}", albumName, artistName);
        String url = buildAlbumQueryUrl(artistName, albumName);
        HttpEntity<String> request = buildRetrieveAlbumRequest();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return gson.fromJson(response.getBody(), AlbumQueryResult.class);
    }

    //should look like this -> artist:Billie%20Marten%20album:Drop%20Cherries
    private String buildAlbumQueryUrl(String artistName, String albumName) {
        String[] artistNameParts = artistName.split(" ");
        String[] albumNameParts = albumName.split(" ");

        StringBuilder artistNameQueryStringBuilder = new StringBuilder();
        StringBuilder albumNameQueryStringBuilder = new StringBuilder();

        artistNameQueryStringBuilder.append("artist:");
        albumNameQueryStringBuilder.append("album:");

        Arrays.stream(artistNameParts).forEach(artistNameQueryStringBuilder::append);
        IntStream.range(0, albumNameParts.length).forEach(i -> {
            albumNameQueryStringBuilder.append(albumNameParts[i]);

            if (i != albumNameParts.length - 1) {
                albumNameQueryStringBuilder.append("%s");
            }
        });

        String artistNameQuery = artistNameQueryStringBuilder.toString();
        String albumNameQuery = albumNameQueryStringBuilder.toString();
        String query = String.format("%s%s", artistNameQuery, albumNameQuery);

        return "https://api.spotify.com/v1/search?q=remaster%20" + query + "&type=album&market=GB&limit=1&offset=0";
    }

    private HttpEntity<String> buildRetrieveAlbumRequest() {
        return new HttpEntity<>(createRetrieveAlbumHeaders());
    }

    private HttpHeaders createRetrieveAlbumHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));

        return headers;
    }




    //THIS WORKS DO NOT DELETE.
    public AlbumQueryResult retrieveAlbumNew() {
        String url = "https://api.spotify.com/v1/search?q=remaster%20artist:Billie%20Marten%20album:Drop%20Cherries&type=album&market=GB&limit=1&offset=0"; //works, so fkn weird.
        HttpEntity<String> request = buildRetrieveAlbumRequest();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        return gson.fromJson(response.getBody(), AlbumQueryResult.class);
    }
}
