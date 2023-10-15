package orgo.backend.domain._6notification.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import orgo.backend.domain._6notification.vo.Notification;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class NotificationService {
    @Value("${fcm.key.path}")
    private String FCM_PRIVATE_KEY_PATH;

    @Value("${fcm.key.scope}")
    private String FIRE_BASE_SCOPE;

    // fcm 기본 설정 진행
    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials
                            .fromStream(new ClassPathResource(FCM_PRIVATE_KEY_PATH).getInputStream())
                            .createScoped(List.of(FIRE_BASE_SCOPE)))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase 서비스 초기화 성공");
            }
        } catch (IOException e) {
            log.error("Firebase 서비스 초기화 중 오류 발생 : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendMessage(Notification notification) {
        try {
            FirebaseMessaging.getInstance().send(notification.getMessage());
        } catch (FirebaseMessagingException e) {
            log.error("메시지 전송에 실패했습니다. : {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public void sendAllMessages(List<Notification> notifications) {
        if (notifications.isEmpty()){
            return;
        }
        try {
            List<Message> messages = notifications.stream()
                    .map(Notification::getMessage)
                    .toList();
            FirebaseMessaging.getInstance().sendAll(messages);
        } catch (FirebaseMessagingException e) {
            log.error("메시지 전송에 실패했습니다. : {}", e.getMessage());
        }
    }
}
