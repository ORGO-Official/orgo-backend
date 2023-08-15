package orgo.backend.domain._2user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import orgo.backend.domain._1auth.domain.LoginType;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Gender {
    FEMALE(Map.of(LoginType.NAVER, "F", LoginType.KAKAO, "female")),
    MALE(Map.of(LoginType.NAVER, "M", LoginType.KAKAO, "male")),
    UNDEFINED(Map.of(LoginType.NAVER, "None", LoginType.KAKAO, "None"));

    private final Map<LoginType, String> map;
    public static Gender findBy(LoginType loginType, String name) {
        if (Objects.isNull(name)){
            return UNDEFINED;
        }
        return Stream.of(values())
                .filter(e -> e.getMap().get(loginType).equals(name))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
