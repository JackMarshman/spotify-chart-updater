package spotifychartupdater.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spotifychartupdater.client.SpotifyClient;
import spotifychartupdater.config.SpotifyChartUpdaterProperties;
import spotifychartupdater.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotifyChartUpdaterService {

    private final Gson gson;
    private final SpotifyClient spotifyClient;

    public Playlist retrievePlaylistById(String playlistId) {
        log.info("Querying Spotify for playlist with ID: {}.", playlistId);
        final String url = String.format("https://api.spotify.com/v1/playlists/%s", playlistId);
        final ResponseEntity<String> response = spotifyClient.retrieveDataFromSpotify(url);

        return gson.fromJson(response.getBody(), Playlist.class);
    }

    public AlbumQueryResult retrieveAlbumByArtist(String artistName, String albumName) {
        log.info("Querying Spotify for album: {} by artist: {}.", albumName, artistName);
        final String url = buildAlbumQueryUrl(artistName, albumName);
        final ResponseEntity<String> response = spotifyClient.retrieveDataFromSpotify(url);

        return gson.fromJson(response.getBody(), AlbumQueryResult.class);
    }

    //TODO - only required for any chart entries where lastMonth isn't NEW or RE.
    //Logic like this is used to determine what queries we need to make to Spotify for new albums.
    public boolean DoesPlaylistContainChartEntry(Playlist playlist, ChartEntry chartEntry) {
        final List<TrackItem> playlistTrackItems = playlist.getTracks().getItems();

        return IntStream.range(0, playlistTrackItems.size())
                .anyMatch(i -> DoesTrackMatchChartEntry(playlistTrackItems.get(i).getTrack(), chartEntry.getAlbumName(), chartEntry.getArtistName()));
    }

    private boolean DoesTrackMatchChartEntry(Track track, String albumName, String artistName) {
        return track.getAlbum().getName().equals(albumName) && DoesAlbumArtistsContainArtistName(track.getArtists(), artistName);
    }

    private boolean DoesAlbumArtistsContainArtistName(List<Artist> artists, String artistName) {
        return artists.stream().anyMatch(artist -> artist.getName().equals(artistName));
    }

    //TODO - refine.
    public String retrieveArtistId(List<Artist> artists, String artistName) {
        String artistId = null;

        for (Artist artist : artists) {
            if (artist.getName().equals(artistName)) {
                artistId = artist.getId();
            }
        }

        return artistId;
    }

    //TODO - refine.
    public Album findValidAlbum(String artistName, String albumName, AlbumItems albumItems) throws Exception {
        List<Album> albums = albumItems.getItems();

        List<Album> albumsWithCorrectName = albums.stream().filter(album -> album.getName().equals(albumName)).collect(Collectors.toList());

        for (Album album : albumsWithCorrectName) {
            List<Artist> artists = album.getArtists();

            for (Artist artist : artists) {
                if (artist.getName().equals(artistName)) {
                    return album;
                }
            }
        }

        throw new Exception("Cannot find valid album from album query for album: {} by artist: {}.");
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

    //THIS WORKS DO NOT DELETE.
//    public AlbumQueryResult retrieveAlbumNew() {
//        String url = "https://api.spotify.com/v1/search?q=remaster%20artist:Billie%20Marten%20album:Drop%20Cherries&type=album&market=GB&limit=1&offset=0"; //works, so fkn weird.
//        HttpEntity<String> request = buildRetrieveAlbumRequest();
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
//
//        return gson.fromJson(response.getBody(), AlbumQueryResult.class);
//    }
}
