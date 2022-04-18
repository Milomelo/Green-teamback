package site.metacoding.blogv2.web.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.category.Category;
import site.metacoding.blogv2.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRespDto {
    private List<Post> posts;
    private List<Category> categorys;
}
