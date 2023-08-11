package orgo.backend.domain._1auth.application.loginstrategy;

import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._1auth.domain.PersonalData;

public interface LoginStrategy {
    /**
     * 네이버, 카카오 등에서 제공하는 액세스 토큰을 바탕으로, 프로필 조회 API를 호출하여 개인 정보를 반환합니다.
     *
     * @param socialToken 서드파티 액세스 토큰
     * @return 개인 정보 (이름, 이메일, 소셜 아이디, 프로필 이미지)
     */
    PersonalData getPersonalData(String socialToken);
}
