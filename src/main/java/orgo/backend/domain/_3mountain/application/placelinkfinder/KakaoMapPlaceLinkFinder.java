package orgo.backend.domain._3mountain.application.placelinkfinder;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class KakaoMapPlaceLinkFinder implements PlaceLinkFinder {
    @Override
    public String find(String address) {
        return null;
    }
}
