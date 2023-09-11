package orgo.backend.domain._etc.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ImageUploaderTest {
    @Test
    @DisplayName("지정된 디렉토리를 생성한다. 상위 디렉토리가 존재하지 않으면 함께 생성한다. ")
    void test() {
        // given
        File file = new File("C://orgo-test/hello");

        // when
        boolean success = file.mkdirs();

        // then
        assertThat(success).isTrue();
        File file1 = new File("C://orgo-test/hello");
        assertThat(file1.delete()).isTrue();
        File file2 = new File("C://orgo-test");
        assertThat(file2.delete()).isTrue();
    }
}