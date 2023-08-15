package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ServiceToken {
    String accessToken;
    String refreshToken;
}
