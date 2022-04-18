package site.metacoding.blogv2.domain.love;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoveRepository extends JpaRepository<Love, Integer> {

}
