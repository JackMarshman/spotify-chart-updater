package spotifychartupdater.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spotifychartupdater.model.AlbumQueryResult;
import spotifychartupdater.model.Playlist;
import spotifychartupdater.service.SpotifyChartUpdaterService;

@RestController
@RequestMapping("/spotify")
@Slf4j
@RequiredArgsConstructor
public class SpotifyChartUpdaterController {

    private final SpotifyChartUpdaterService spotifyChartUpdaterService;

    @GetMapping(value = "/album", produces = MediaType.APPLICATION_JSON_VALUE)
    public AlbumQueryResult album() {
        return spotifyChartUpdaterService.retrieveAlbumByArtist("Danny Brown", "SCARING THE HOES");
    }

    @GetMapping(value = "/playlist", produces = MediaType.APPLICATION_JSON_VALUE)
    public Playlist playlist() {
        return spotifyChartUpdaterService.retrievePlaylistById("0Vokgw8xnF8hmqR5Oz9wMA");
    }

    //TODO - Main entry point of application.
    @PostMapping(value = "/update", consumes = "text/csv")
    public void update(@RequestBody String csvData) {

    }
}
