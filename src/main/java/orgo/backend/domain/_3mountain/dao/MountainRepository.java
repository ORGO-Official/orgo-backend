package orgo.backend.domain._3mountain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import orgo.backend.domain._3mountain.domain.Mountain;

import java.util.List;
import java.util.Optional;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    @Query(" select m from Mountain m " + "  where m.name like %:keyword% ")
    List<Mountain> findByKeyword(String keyword);
}
