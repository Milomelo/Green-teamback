package site.metacoding.blogv2.web.Dto;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.category.Category;
import site.metacoding.blogv2.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryWriteReqDto {

    @Column(length = 60, nullable = false)
    private String title;

    public Category toEntity(User principal) {
        Category category = new Category();
        category.setTitle(title);
        category.setUser(principal);
        return category;
    }
}
