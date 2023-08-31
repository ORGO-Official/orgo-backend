package orgo.backend.domain._1auth.vo;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ServiceToken {
    String accessToken;
    String refreshToken;
}
