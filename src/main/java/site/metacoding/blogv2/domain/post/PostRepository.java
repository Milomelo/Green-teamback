package site.metacoding.blogv2.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post WHERE title like %:keyword%", nativeQuery = true)
    Page<Post> findByTitleContaining(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE userId = :userId AND title like %:mykeyword%", nativeQuery = true)
    Page<Post> mfindByUserID(@Param("userId") Integer userId, @Param("mykeyword") String mykeyword, Pageable pageable);

}
