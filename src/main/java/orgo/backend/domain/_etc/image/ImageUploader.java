package orgo.backend.domain._etc.image;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import orgo.backend.global.error.exception.InternalServerException;

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

    public final String DEFAULT_PROFILE_IMAGE = SERVER_ADDRESS + IMAGE_STORAGE_PATH + ImageType.PROFILE.getDirectory() + "default_profile_image.png";

    /**
     * 이미지 파일을 업로드합니다.
     * storedPath 위치에 업로드됩니다.
     * 저장되는 파일의 이름은 UUID 랜덤 문자열입니다.
     *
     * @param multipartFile 멀티파트 파일
     * @param imageType     이미지 분류
     * @return 이미지 URL
     */
    public String upload(MultipartFile multipartFile, ImageType imageType) {
        requireNonNull(multipartFile);
        String originalName = multipartFile.getOriginalFilename();
        String storedPath = makeStoredPath(imageType, originalName);
        transferFile(multipartFile, storedPath);
        return SERVER_ADDRESS + storedPath;
    }

    /**
     * 이미지가 저장될 Path를 생성합니다.
     * 이미지 타입에 따라 상위 디렉토리가 결정됩니다.
     *
     * @param imageType    이미지 타입
     * @param originalName 원본 이름
     * @return Path
     */
    @NotNull
    private String makeStoredPath(ImageType imageType, String originalName) {
        return IMAGE_STORAGE_PATH + imageType.getDirectory() + makeRandomNameWithExtension(originalName);
    }

    private void transferFile(MultipartFile multipartFile, String storedPath) {
        File destination = new File(storedPath);
        try {
            multipartFile.transferTo(destination);
        } catch (IOException e) {
            log.error("파일 업로드에 실패했습니다.");
            throw new InternalServerException();
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
     * @param imageUrl 삭제할 이미지 URL
     * @throws IOException
     */
    public boolean deleteIfExists(String imageUrl) throws IOException {
        File file = new File(getRelativePath(imageUrl));
        return Files.deleteIfExists(Path.of(file.getAbsolutePath()));
    }

    /**
     * 이미지 URL에서 서버 주소를 제외한 경로를 추출합니다.
     *
     * @param imageUrl 이미지 URL
     * @return 이미지 URL 중 서버 주소를 제외한 부분
     */
    private String getRelativePath(String imageUrl) {
        return imageUrl.substring(SERVER_ADDRESS.length());
    }
}
