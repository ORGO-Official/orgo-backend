package orgo.backend.domain._1auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PersonalData {
    String name;
    String email;
    String birthdate;
    String profileImageUrl;
    String mobile;
    String socialId;
    String source;
}
