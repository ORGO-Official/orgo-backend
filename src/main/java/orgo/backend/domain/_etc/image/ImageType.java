package orgo.backend.domain._etc.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ImageType {
    PROFILE("profiles");

    private final String directoryName;
}
