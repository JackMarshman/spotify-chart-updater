package spotifychartupdater.model;

import lombok.Data;

import java.util.List;

//TODO - potentially restructure this to be more in line with Playlist??
@Data
public class AlbumQueryResult {

    private AlbumItems albums;
}