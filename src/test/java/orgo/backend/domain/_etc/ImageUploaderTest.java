package orgo.backend.domain._etc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import orgo.backend.domain._etc.image.ImageType;
import orgo.backend.domain._etc.image.ImageUploader;
import orgo.backend.setting.MockEntityFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
public class ImageUploaderTest{
    @Autowired
    ImageUploader imageUploader;

    @Test
    @DisplayName("ImageUploader를 이용해 이미지 파일을 저장하고, 삭제한다.")
    void test1() throws IOException {
        // given
        MockMultipartFile mockMultipartFile = MockEntityFactory.mockMultipartFileImage("file");

        // when
        String imageUrl = imageUploader.upload(mockMultipartFile, ImageType.PROFILE);

        // then
        assertThat(imageUploader.deleteIfExists(imageUrl)).isTrue();
    }

    @Test
    @DisplayName("빈 MultipartFile을 업로드 시도하는 경우 예외가 발생한다. ")
    void test2()  {
        // given

        // when

        // then
        assertThatThrownBy(() -> imageUploader.upload(null, ImageType.PROFILE)).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("삭제하려는 파일이 없는 경우 삭제하지 않는다.")
    void test3() throws IOException {
        // given
        String imageUrl = "empty";

        // when
        boolean result = imageUploader.deleteIfExists(imageUrl);

        // then
        assertThat(result).isFalse();
    }
}
