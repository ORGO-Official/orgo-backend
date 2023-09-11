package orgo.backend.domain._2user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import orgo.backend.domain._2user.repository.UserRepository;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._2user.dto.UserProfileDto;
import orgo.backend.domain._etc.image.ImageType;
import orgo.backend.domain._etc.image.ImageUploader;
import orgo.backend.global.error.exception.UserNotFoundException;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;

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
     * @param userId     사용자 아이디넘버
     * @param requestDto 수정할 항목
     */
    public void updateProfile(Long userId, UserProfileDto.Request requestDto, MultipartFile imageFile) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        String imageUrl = getImageUrl(imageFile);
        user.updateProfile(requestDto.getNickname(), imageUrl);
    }

    private String getImageUrl(MultipartFile imageFile) throws IOException {
        if (imageFile == null) {
            return imageUploader.getDefaultProfileImage();
        }
        return imageUploader.upload(imageFile, ImageType.PROFILE);
    }
}
