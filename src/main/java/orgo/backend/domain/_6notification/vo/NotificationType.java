package orgo.backend.domain._6notification.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NotificationType {
    BASIC("basic"),
    BADGE("badge");
    private final String value;

    public String value(){
        return this.value;
    }
}
