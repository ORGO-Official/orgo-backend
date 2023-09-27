package orgo.backend.domain._6notification.vo;

import com.google.firebase.messaging.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import orgo.backend.domain._2user.entity.User;

@Getter
@AllArgsConstructor
public class Notification {

    private final static String TITLE_FIELD = "title";
    private final static String BODY_FIELD = "body";
    private final static String TYPE_FIELD = "type";
    private final static String BADGE_FIELD = "badgeId";

    private Message message;

    public static Notification basicNotification(User user, String title, String body) {
        Message message = Message.builder()
                .putData(TITLE_FIELD, title)
                .putData(BODY_FIELD, body)
                .putData(TYPE_FIELD, NotificationType.BASIC.value())
                .setToken(user.getFcmToken())
                .build();
        return new Notification(message);
    }

    public static Notification badgeNotification(User user, long badgeId) {
        final String title = "새로운 뱃지를 획득했어요!";
        final String body = "지금 눌러서 확인하기";
        Message message = Message.builder()
                .putData(TITLE_FIELD, title)
                .putData(BODY_FIELD, body)
                .putData(TYPE_FIELD, NotificationType.BADGE.value())
                .putData(BADGE_FIELD, String.valueOf(badgeId))
                .setToken(user.getFcmToken())
                .build();
        return new Notification(message);
    }
}
