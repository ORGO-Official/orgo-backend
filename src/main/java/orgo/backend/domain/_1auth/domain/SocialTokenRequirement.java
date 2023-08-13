package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 소셜 토큰 API를 호출하기 위해 필요한 정보를 나타내는 클래스입니다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SocialTokenRequirement {
    protected String code;
    protected String state;
    protected String redirectUri;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocialTokenRequirement that = (SocialTokenRequirement) o;
        return Objects.equals(code, that.code) && Objects.equals(state, that.state) && Objects.equals(redirectUri, that.redirectUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, state, redirectUri);
    }
}
