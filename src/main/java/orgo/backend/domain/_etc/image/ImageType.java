package orgo.backend.domain._etc.image;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@AllArgsConstructor
@Getter
public enum ImageType {
    PROFILE(File.separator + "profiles" + File.separator);

    private final String directory;
}
