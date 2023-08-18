package orgo.backend.domain._etc.image;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class ImageUploader {
    @Value("${orgo-server.path.storage.images}")
    String IMAGE_STORAGE_PATH;
    @Value("${orgo-server.address}")
    String SERVER_ADDRESS;

    /**
     * 이미지 파일을 업로드합니다.
     * storedPath 위치에 업로드됩니다.
     * 저장되는 파일의 이름은 UUID 랜덤 문자열입니다.
     *
     * @param multipartFile 멀티파트 파일
     * @param imageType     이미지가 저장될 하위 디렉토리 (이미지 종류에 따라 분류)
     * @return 이미지 정보
     */
    public Image upload(MultipartFile multipartFile, ImageType imageType) {
        requireNonNull(multipartFile);
        String originalName = multipartFile.getOriginalFilename();
        String storedName = makeRandomNameWithExtension(originalName);
        String storedPath = IMAGE_STORAGE_PATH + File.separator + imageType.getDirectoryName() + File.separator + storedName;
        transferFile(multipartFile, storedPath);
        return new Image(storedName, SERVER_ADDRESS + storedPath);
    }

    private void transferFile(MultipartFile multipartFile, String storedPath) {
        File destination = new File(storedPath);
        try {
            multipartFile.transferTo(destination);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다. ");
        }
    }

    private void requireNonNull(MultipartFile multipartFile) {
        Objects.requireNonNull(multipartFile);
        Objects.requireNonNull(multipartFile.getOriginalFilename());
    }

    private String makeRandomNameWithExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    /**
     * 저장된 이미지 파일을 삭제합니다.
     *
     * @param image 삭제할 이미지 정보
     * @throws IOException
     */
    public boolean deleteIfExists(Image image) throws IOException {
        File file = new File(getRelativePath(image));
        return Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }

    /**
     * 이미지 URL에서 서버 주소를 제외한 경로를 추출합니다.
     *
     * @param image 이미지 정보
     * @return 이미지 URL 중 서버 주소를 제외한 부분
     */
    private String getRelativePath(Image image) {
        String fullPath = image.getImageUrl();
        return fullPath.substring(SERVER_ADDRESS.length());
    }
}
