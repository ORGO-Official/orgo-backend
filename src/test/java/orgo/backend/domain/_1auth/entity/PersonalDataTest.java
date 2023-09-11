package orgo.backend.domain._1auth.entity;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
class PersonalDataTest {
    @Test
    @DisplayName("birthday와 birthyear를 조합해 LocalDateTime 포맷 데이터를 생성한다.")
    void test() {
        // given
        String birthyear = "1999";
        String birthday = "10-01";

        // when
        String birthMonthDay = birthday.replace("-", "");

        // then
        LocalDate date = LocalDate.parse(birthyear + birthMonthDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
        Assertions.assertThat(date).isEqualTo(LocalDate.of(1999, 10, 1));
    }
}