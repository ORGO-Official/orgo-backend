package orgo.backend.domain._3mountain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import orgo.backend.domain._3mountain.entity.Difficulty;
import orgo.backend.domain._3mountain.entity.FeatureTag;
import orgo.backend.domain._3mountain.entity.Location;
import orgo.backend.domain._3mountain.entity.Mountain;

public class MountainDto {
    @NoArgsConstructor
    @Getter
    @ToString
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private String address;
        private String contact;
        private String mainImage;
        private String backgroundImage;
        private String requiredTime;
        private Difficulty difficulty;
        private Location location;
        private FeatureTag featureTag;

        public Response(Mountain mountain) {
            this.id = mountain.getId();
            this.name = mountain.getName();
            this.description = mountain.getDescription();
            this.address = mountain.getAddress();
            this.contact = mountain.getContact();
            this.mainImage = mountain.getMainImage();
            this.backgroundImage = mountain.getBackgroundImage();
            this.requiredTime = mountain.getRequiredTime();
            this.difficulty = mountain.getDifficulty();
            this.location = mountain.getLocation();
            this.featureTag = mountain.getFeatureTag();
        }
    }
}
