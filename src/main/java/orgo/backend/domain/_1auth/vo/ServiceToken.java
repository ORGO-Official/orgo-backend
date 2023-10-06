package orgo.backend.domain._1auth.vo;

import lombok.*;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ServiceToken {
    String accessToken;
    String refreshToken;
}
