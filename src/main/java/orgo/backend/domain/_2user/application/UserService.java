package orgo.backend.domain._2user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._2user.dto.UserProfile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 사용자 프로필을 조회합니다.
     *
     * @param userId 사용자 아이디넘버
     * @return 프로필 정보
     */
    public UserProfile getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        return new UserProfile(user);
    }
}
