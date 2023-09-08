package orgo.backend.domain._3mountain.dto;

import lombok.Getter;
import lombok.ToString;
import orgo.backend.domain._3mountain.entity.PlaceInfo;

public class RestaurantDto {
    @Getter
    @ToString
    public static class Response{
        private final String name;
        private final String address;
        private final double distance;
        private final double mapX;
        private final double mapY;
        private final String contact;
        private final String imageUrl;
        private final String externalLink;

        public Response(PlaceInfo placeInfo){
            this.name = placeInfo.getName();
            this.address = placeInfo.getAddress();
            this.distance = placeInfo.getDistance();
            this.mapX = placeInfo.getMapX();
            this.mapY = placeInfo.getMapY();
            this.contact = placeInfo.getContact();
            this.imageUrl = placeInfo.getImageUrl();
            this.externalLink = placeInfo.getExternalLink();
        }
    }
}
