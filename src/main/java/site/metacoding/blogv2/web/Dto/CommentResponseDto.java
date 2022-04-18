package site.metacoding.blogv2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.comment.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponseDto {
    private Comment comment;
    private boolean auth;
}
