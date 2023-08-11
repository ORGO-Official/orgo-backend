package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import orgo.backend.domain._1auth.application.NaverLoginStrategy;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum LoginType {
    NAVER("naver", NaverLoginStrategy.class);


    private final String name;
    private final Class<?> strategy;

    public static LoginType findBy(String name) {
        return Stream.of(values())
                .filter(e -> e.getName().equals(name))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
