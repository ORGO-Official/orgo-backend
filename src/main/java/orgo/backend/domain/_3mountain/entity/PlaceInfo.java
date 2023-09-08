package orgo.backend.domain._3mountain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import orgo.backend.domain._3mountain.service.placesearcher.OpenPortalPlaceSearcher;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class PlaceInfo {
    private final String name;
    private final String address;
    private final double distance;
    private final double mapX;
    private final double mapY;
    private final String contact;
    private final String imageUrl;
    private String externalLink;

    public static PlaceInfo fromOpenPortalPlaceSearcher(OpenPortalPlaceSearcher.ResponseFormat.Response.Body.Items.Item responseData) {
        return PlaceInfo.builder()
                .name(responseData.getTitle())
                .address(responseData.getAddr1())
                .distance(Double.parseDouble(responseData.getDist()))
                .mapX(Double.parseDouble(responseData.getMapx()))
                .mapY(Double.parseDouble(responseData.getMapy()))
                .contact(responseData.getTel())
                .imageUrl(responseData.getFirstimage())
                .externalLink(null)
                .build();
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public boolean hasLink(){
        return this.externalLink != null;
    }
}
