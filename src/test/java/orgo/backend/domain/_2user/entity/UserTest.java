package orgo.backend.domain._2user.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;
import orgo.backend.setting.MockEntityFactory;

class UserTest {

    @Test
    @DisplayName("사용자가 해당 뱃지를 보유하고 있을 경우, True를 반환한다.")
    void hasBadge(){
        //given
        User user = MockEntityFactory.mockUser(1L);
        Badge badge = MockEntityFactory.mockBadge(null);
        Acquisition acquisition = new Acquisition(badge, user);
        user.getAcquisitions().add(acquisition);

        //when
        boolean result = user.haveBadge(badge);

        //then
        Assertions.assertThat(result).isTrue();
    }



}