package orgo.backend.domain._5badge.vo;

import lombok.Getter;
import orgo.backend.domain._2user.entity.User;
import orgo.backend.domain._3mountain.entity.Mountain;

@Getter
public class RecordCountCondition extends CheckCondition{
    private final User user;
    private final Mountain mountain;

    public RecordCountCondition(User user, Mountain mountain) {
        this.user = user;
        this.mountain = mountain;
    }
}
