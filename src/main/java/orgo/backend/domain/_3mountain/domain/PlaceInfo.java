package orgo.backend.domain._3mountain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import orgo.backend.domain._3mountain.application.placesearcher.OpenPortalPlaceSearcher;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class PlaceInfo {
    private final String name;
    private final String address;
    private final String mapX;
    private final String mapY;
    private final String contact;
    private final String imageUrl;

    public static PlaceInfo fromOpenPortalPlaceSearcher(OpenPortalPlaceSearcher.ResponseData responseData) {
        return PlaceInfo.builder()
                .name(responseData.getTitle())
                .address(responseData.getAddr1())
                .mapX(responseData.getMapx())
                .mapY(responseData.getMapy())
                .contact(responseData.getTel())
                .imageUrl(responseData.getFirstimage())
                .build();
    }

}
