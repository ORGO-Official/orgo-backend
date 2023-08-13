package orgo.backend.domain._1auth.application.loginstrategy;

import orgo.backend.domain._1auth.domain.PersonalData;
import orgo.backend.domain._1auth.dto.SocialTokenRequirement;
import orgo.backend.domain._1auth.domain.SocialToken;

public interface LoginStrategy {
    /**
     * 네이버, 카카오 등에서 제공하는 액세스 토큰을 바탕으로, 프로필 조회 API를 호출하여 개인 정보를 반환합니다.
     *
     * @param socialToken 서드파티 액세스 토큰
     * @return 개인 정보 (이름, 이메일, 소셜 아이디, 프로필 이미지)
     */
    PersonalData getPersonalData(String socialToken);

    /**
     * Callback으로 전달받은 정보를 이용하여 서드파티 앱의 소셜 토큰을 발급받습니다.
     *
     * @return 서드파티 액세스 토큰
     */
    SocialToken createSocialToken(SocialTokenRequirement socialTokenRequirement);

    /**
     * 소셜 리프레시 토큰을 사용해 소셜 토큰을 재발급합니다.
     *
     * @param refreshToken 소셜 리프레시 토큰
     * @return 재발급한 소셜 토큰
     */
    SocialToken reissueSocialToken(String refreshToken);

    /**
     * 소셜 로그인의 연동을 해제합니다.
     * 사용한 socialToken이 즉시 만료처리됩니다.
     *
     * @param socialToken 서드파티 액세스 토큰
     */
    void unlink(String socialToken);
}
