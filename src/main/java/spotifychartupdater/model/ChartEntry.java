package spotifychartupdater.model;

import lombok.Data;

@Data
public class ChartEntry {

    private String lastMonth;
    private int thisMonth;
    private String albumName;
    private String artistName;
}
