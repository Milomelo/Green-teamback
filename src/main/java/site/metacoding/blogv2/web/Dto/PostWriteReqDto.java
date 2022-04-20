package site.metacoding.blogv2.web.dto;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;

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
}
