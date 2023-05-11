package spotifychartupdater.model;

import lombok.Data;

import java.util.List;

@Data
public class AlbumItems {

    public String href;
    public List<AlbumItem> items;
    public int limit;
    public String next;
    public int offset;
    public String previous;
    public int total;
}
