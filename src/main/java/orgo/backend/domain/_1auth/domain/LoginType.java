package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import orgo.backend.domain._1auth.application.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.application.loginstrategy.NaverLoginStrategy;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum LoginType {
    NAVER("naver", NaverLoginStrategy.class),
    KAKAO("kakao", KakaoLoginStrategy.class);


    private final String name;
    private final Class<?> strategy;

    public static LoginType findBy(String name) {
        return Stream.of(values())
                .filter(e -> e.getName().equals(name))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
