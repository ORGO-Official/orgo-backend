package orgo.backend.domain._etc.image;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.MimeType;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Image {
    String ImageName;
    String ImageUrl;

    public static Image newDefaultProfileImage() {
        return new Image("default_profile_image.png", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        return ImageName.equals(image.ImageName) && ImageUrl.equals(image.ImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ImageName, ImageUrl);
    }
}
