package spotifychartupdater.model;

import lombok.Data;

import java.util.List;

@Data
public class Tracks {

    private String href;
    private List<TrackItem> items;
}
