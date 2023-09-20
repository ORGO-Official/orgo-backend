package orgo.backend.domain._5badge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


public class BadgeDto {
    @Getter
    @AllArgsConstructor
    public static class Acquired{
        private long id;
        private String condition;
        private String description;
        private LocalDateTime acquiredTime;

    }

}
