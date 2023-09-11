package orgo.backend.domain._2user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._1auth.entity.LoginType;
import orgo.backend.domain._2user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findBySocialIdAndLoginType(String socialId, LoginType loginType);
}
