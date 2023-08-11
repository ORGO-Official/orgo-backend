package orgo.backend.domain._1auth.application;

import orgo.backend.domain._1auth.domain.ServiceToken;

public interface AuthService {

    ServiceToken login();

    void logout(ServiceToken token, Long userId);

    void withdraw(ServiceToken token, Long userId);
}
