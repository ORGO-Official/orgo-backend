package orgo.backend.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.*;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.RecordCountBadge;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MockEntityFactory {

    public static User mockUser(Long id) {
        return User.builder()
                .id(id)
                .nickname("테스트유저")
                .email("hansol8701@test.com")
                .socialId("123456")
                .profileImage("기본 프로필 이미지")
                .loginType(LoginType.NAVER)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    public static Peak mockPeak(Long id) {
        return Peak.builder()
                .id(id)
                .name("에베레스트산 정상")
                .location(new Location(36.103958, 128.423648, 352.168))
                .isTop(true)
                .build();
    }

    public static Mountain mockMountain(Long id, Peak... peaks) {
        return Mountain.builder()
                .id(id)
                .name("에베레스트산")
                .description("세계에서 가장 높은 산")
                .address("경상북도 구미시 여헌로 15-11")
                .contact("010-1234-5678")
                .mainImage("image.jpg")
                .backgroundImage("image.jpg")
                .requiredTime("1시간 ~ 2시간")
                .difficulty(Difficulty.HARD)
                .location(new Location(36.103958, 128.423648, 352.168))
                .featureTag(new FeatureTag(true, 1, true, true, true))
                .peaks(Arrays.asList(peaks))
                .climbingRecords(new ArrayList<>())
                .build();

    }

    public static Badge mockBadge(Long id, Mountain mountain){
        return RecordCountBadge.builder()
                .mountain(mountain)
                .count(1)
                .build();
    }


    public static MockMultipartFile mockMultipartFileImage(String paramName) throws IOException {
        String ORIGINAL_FILE_NAME = "text-image.jpg";
        String CONTENT_TYPE = MediaType.IMAGE_JPEG_VALUE;
        String PATH = "src/test/java/orgo/backend/setting/files/text-image.jpg";
        FileInputStream fileInputStream = new FileInputStream(PATH);
        return new MockMultipartFile(paramName, ORIGINAL_FILE_NAME, CONTENT_TYPE, fileInputStream);
    }

    public static MockMultipartFile mockMultipartFileJson(String paramName, Object object) throws IOException {
        String ORIGINAL_FILE_NAME = "objects.json";
        String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
        byte[] DATA = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsBytes(object);
        return new MockMultipartFile(paramName, ORIGINAL_FILE_NAME, CONTENT_TYPE, DATA);
    }

}
