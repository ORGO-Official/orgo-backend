package orgo.backend.domain._3mountain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import orgo.backend.domain._3mountain.entity.Mountain;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    @Query(" select m from Mountain m " + "  where m.name like %:keyword% ")
    List<Mountain> findByKeyword(String keyword);
}
