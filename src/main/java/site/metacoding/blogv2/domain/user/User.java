package site.metacoding.blogv2.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.comment.Comment;
import site.metacoding.blogv2.domain.post.Post;

import javax.persistence.Transient;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) // 이 부분 추가
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    // 1234 -> SHA256(해시 알고리즘) -> AB4539GDUF3AE -> 이렇게 안하면 시큐리티 거부
    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 60, nullable = false)
    private String email;

    private String imgurl; // 사진의 경로

    private String blogname;

    @Column(nullable = true)
    private String blogtitle;

    @OneToMany(mappedBy = "user") // 연관관계의 주인의 변수명
    private List<Post> post;

    @JsonIgnore
    @OneToMany(mappedBy = "user") // 연관관계의 주인의 변수명
    private List<Comment> comments;

    @CreatedDate // insert 할때만 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update 할때만 동작
    private LocalDateTime updateDate;

    @Transient
    private String remember;

}