package spotifychartupdater.model;

import lombok.Data;

import java.util.List;

@Data
public class AlbumItems {

    private String href;
    private List<Album> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
}
