package orgo.backend.domain._5badge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import orgo.backend.domain._5badge.entity.Badge;
import orgo.backend.domain._5badge.entity.acquisition.Acquisition;

import java.time.LocalDateTime;


public class BadgeDto {
    @Getter
    @AllArgsConstructor
    public static class Acquired{
        private long id;
        private String objective;
        private String description;
        private LocalDateTime acquiredTime;

        public Acquired(Acquisition acquisition){
            Badge badge = acquisition.getBadge();
            this.id = badge.getId();
            this.objective = badge.getObjective();
            this.description = badge.getDescription();
            this.acquiredTime = acquisition.getCreatedTime();
        }
    }

}
