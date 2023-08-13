package orgo.backend.domain._1auth.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import orgo.backend.domain._1auth.dao.SocialTokenRepository;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.setting.RepositoryTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class SocialTokenRepositoryTest extends RepositoryTest {
    @Autowired
    SocialTokenRepository socialTokenRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User가 저장될 때, SocialToken도 함께 저장된다. ")
    void test() {
        // given
        User user = User.builder()
                .build();

        SocialToken socialToken = new SocialToken(null, "access", "refresh");

        // when
        user.setSocialToken(socialToken);
        User saved = userRepository.save(user);

        // then
        Optional<User> found = userRepository.findById(saved.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getSocialToken()).isNotNull();
    }

    @Test
    @DisplayName("User가 제거될 때, SocialToken도 함께 제거된다. ")
    void test1() {
        // given
        SocialToken socialToken = new SocialToken(null, "access", "refresh");
        User user = User.builder()
                .socialToken(socialToken)
                .build();
        User saved = userRepository.save(user);
        Long oldTokenId = saved.getSocialToken().getId();

        // when
        userRepository.delete(saved);

        // then
        assertThat(socialTokenRepository.findById(oldTokenId)).isEmpty();
    }
}