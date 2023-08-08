package orgo.backend.domain._2user.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import orgo.backend.domain._2user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
