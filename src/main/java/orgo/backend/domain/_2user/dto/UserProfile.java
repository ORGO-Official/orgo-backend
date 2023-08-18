package orgo.backend.domain._2user.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._2user.domain.User;

@Getter
public class UserProfile {
    private final Long id;
    private final String nickname;
    private final String email;
    private final String profileImage;
    LoginType loginType;

    public UserProfile(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.profileImage = "";
        this.loginType = user.getLoginType();
    }
}
