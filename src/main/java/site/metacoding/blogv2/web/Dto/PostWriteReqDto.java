package site.metacoding.blogv2.web.dto;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.category.Category;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostWriteReqDto {

    @Column(nullable = true)
    private Integer categoryId;

    @Column(length = 60, nullable = false)
    private String title;

    @Column(nullable = true)
    private MultipartFile thumnailFile; // 섬네일은 null 허용

    @Column(nullable = true)
    private String content; // 컨텐트 null 허용

    private String secret;

    public Post toEntity(String thumnail, User principal, Category category) {
        Post posts = new Post();
        posts.setTitle(title);
        posts.setContent(content);
        posts.setThumnail(thumnail);
        posts.setUser(principal);
        posts.setCategory(category);
        posts.setSecret(secret);
        return posts;
    }
}
