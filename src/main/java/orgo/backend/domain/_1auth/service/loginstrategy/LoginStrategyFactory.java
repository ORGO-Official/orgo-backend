package orgo.backend.domain._1auth.service.loginstrategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import orgo.backend.domain._1auth.entity.LoginType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class LoginStrategyFactory {
    private final Map<String, LoginStrategy> strategies;

    @Autowired
    public LoginStrategyFactory(Set<LoginStrategy> strategySet) {
        strategies = new HashMap<>();
        for (LoginStrategy strategy : strategySet) {
            strategies.put(strategy.getClass().getSimpleName(), strategy);
        }
    }

    public LoginStrategy findStrategy(LoginType loginType) {
        return strategies.get(loginType.getStrategy().getSimpleName());
    }
}
