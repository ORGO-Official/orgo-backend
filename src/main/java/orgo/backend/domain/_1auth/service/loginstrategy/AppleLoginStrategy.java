package orgo.backend.domain._1auth.service.loginstrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import orgo.backend.domain._1auth.vo.PersonalData;

@Component
@Slf4j
public class AppleLoginStrategy implements LoginStrategy {
    private final static String DELIMITER = "\\|";

    @Override
    public PersonalData getPersonalData(String socialToken) {
        AppleProfile appleProfile = AppleProfile.parse(socialToken);
        return PersonalData.fromApple(appleProfile);
    }

    @Override
    public void logout(String socialToken) {
    }

    @Override
    public void unlink(String socialToken) {
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppleProfile {
        String id;
        String email;

        static AppleProfile parse(String socialToken) {
            String[] token = socialToken.split(DELIMITER);
            String id = token[0];
            String email = token[1];
            return new AppleProfile(id, email);
        }
    }
}
