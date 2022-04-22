package site.metacoding.blogv2.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRespDto {
    private List<Post> posts;
}
