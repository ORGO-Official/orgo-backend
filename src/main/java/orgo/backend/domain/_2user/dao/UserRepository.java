package orgo.backend.domain._2user.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._1auth.domain.LoginType;
import orgo.backend.domain._2user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findBySocialIdAndLoginType(String socialId, LoginType loginType);
}
