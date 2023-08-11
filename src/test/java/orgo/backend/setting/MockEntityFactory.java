package orgo.backend.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import orgo.backend.domain._2user.domain.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

public class MockEntityFactory {

    public static User mockUser() {
        return User.builder()
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }


    public static MockMultipartFile mockMultipartFileImage(String paramName) throws IOException {
        String ORIGINAL_FILE_NAME = "text-image.jpg";
        String CONTENT_TYPE = MediaType.IMAGE_JPEG_VALUE;
        String PATH = "src/test/java/projectbuildup/mivv/integrationtest/setting/files/text-image.jpg";
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
