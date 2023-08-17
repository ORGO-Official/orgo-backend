package orgo.backend.integrationtest._etc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import orgo.backend.global.config.security.CustomUserDetailsService;
import orgo.backend.setting.IntegrationTest;

public class ExceptionTest extends IntegrationTest {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("")
    void test() {
        // given
        customUserDetailsService.loadUserByUsername("1");

        // when

        // then
    }
}
