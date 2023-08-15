package orgo.backend.domain._3mountain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceInfo {
    private final String name;
    private final String address;
    private final String mapX;
    private final String mapY;
    private final String contact;
    private final String imageUrl;
    private final PlaceLink externalLink;
}
