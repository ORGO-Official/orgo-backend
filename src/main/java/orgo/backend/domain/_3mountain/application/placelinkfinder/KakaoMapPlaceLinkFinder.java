package orgo.backend.domain._3mountain.application.placelinkfinder;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import orgo.backend.domain._3mountain.domain.PlaceLink;

@Primary
@Component
public class KakaoMapPlaceLinkFinder implements PlaceLinkFinder {
    @Override
    public PlaceLink find() {
        return null;
    }
}
