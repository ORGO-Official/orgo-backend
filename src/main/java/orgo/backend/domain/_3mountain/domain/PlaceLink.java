package orgo.backend.domain._3mountain.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class PlaceLink {
    String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceLink that = (PlaceLink) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
