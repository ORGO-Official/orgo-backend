package orgo.backend.domain._1auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소셜 토큰 API를 호출하기 위해 필요한 정보를 나타내는 클래스입니다.
 */
public class SocialTokenRequirement {
    protected String code;
    protected String state;
    protected String redirectUri;
}
