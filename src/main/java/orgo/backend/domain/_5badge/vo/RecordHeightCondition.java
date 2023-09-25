package orgo.backend.domain._5badge.vo;

import lombok.Getter;
import orgo.backend.domain._2user.entity.User;

@Getter
public class RecordHeightCondition extends CheckCondition{
    private final User user;

    public RecordHeightCondition(User user) {
        this.user = user;
    }
}
