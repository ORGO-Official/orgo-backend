package orgo.backend.domain._1auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._1auth.domain.SocialToken;

public interface SocialTokenRepository extends JpaRepository<SocialToken, Long> {
}
