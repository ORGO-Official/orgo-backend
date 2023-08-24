package orgo.backend.domain._3mountain.dto;

import lombok.Getter;
import orgo.backend.domain._3mountain.domain.Difficulty;
import orgo.backend.domain._3mountain.domain.FeatureTag;
import orgo.backend.domain._3mountain.domain.Location;
import orgo.backend.domain._3mountain.domain.Mountain;

public class MountainDto {
    @Getter
    public static class Response {
        private final Long id;
        private final String name;
        private final String description;
        private final String address;
        private final String contact;
        private final String mainImage;
        private final String backgroundImage;
        private final String requiredTime;
        private final Difficulty difficulty;
        private final Location location;
        private final FeatureTag featureTag;

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
