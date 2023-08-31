package orgo.backend.domain._1auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import orgo.backend.domain._1auth.service.loginstrategy.AppleLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.KakaoLoginStrategy;
import orgo.backend.domain._1auth.service.loginstrategy.NaverLoginStrategy;
import orgo.backend.global.error.exception.ResourceNotFoundException;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum LoginType {
    NAVER("naver", NaverLoginStrategy.class),
    KAKAO("kakao", KakaoLoginStrategy.class),
    APPLE("apple", AppleLoginStrategy.class);


    private final String name;
    private final Class<?> strategy;

    public static LoginType findBy(String name) {
        return Stream.of(values())
                .filter(e -> e.getName().equals(name))
                .findFirst().orElseThrow(ResourceNotFoundException::new);
    }
}
