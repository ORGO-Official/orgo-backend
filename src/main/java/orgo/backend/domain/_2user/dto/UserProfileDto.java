package orgo.backend.domain._2user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._2user.domain.User;

public class UserProfileDto {

    @Getter
    public static class Response {
        private final Long id;
        private final String nickname;
        private final String email;
        private final String profileImage;
        LoginType loginType;

        public Response(User user) {
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.email = user.getEmail();
            this.profileImage = user.getProfileImage();
            this.loginType = user.getLoginType();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String nickname;
        private String profileImage;
    }
}
