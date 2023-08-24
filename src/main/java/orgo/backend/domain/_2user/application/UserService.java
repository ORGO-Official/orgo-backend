package orgo.backend.domain._2user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._2user.domain.User;
import orgo.backend.domain._2user.dto.UserProfileDto;
import orgo.backend.global.error.exception.UserNotFoundException;

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
    public UserProfileDto.Response getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return new UserProfileDto.Response(user);
    }


    /**
     * 사용자 프로필을 수정합니다.
     * 닉네임과 이미지를 수정할 수 있습니다.
     *
     * @param userId 사용자 아이디넘버
     * @param requestDto 수정할 항목
     */
    public void updateProfile(Long userId, UserProfileDto.Request requestDto) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.updateProfile(requestDto.getNickname(), requestDto.getProfileImage());
    }
}
