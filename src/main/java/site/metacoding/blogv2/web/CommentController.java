package site.metacoding.blogv2.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.comment.Comment;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.CommentService;
import site.metacoding.blogv2.web.dto.ResponseDto;
import site.metacoding.blogv2.web.util.Script;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;
    private final HttpSession session;

    @PostMapping("/s/post/{postId}/comment")
    public @ResponseBody String write(@PathVariable Integer postId, Comment comment) { // x-www-form~~
        User principal = (User) session.getAttribute("principal");
        if (principal != null) {
            comment.setUser(principal);
            commentService.댓글쓰기(comment, postId);
            return "redirect:/post/" + postId;
        } else {

            return Script.href("redirect:/post/", "로그인을 하세요");
        }

    }

    @DeleteMapping("/s/api/comment/{id}")
    public @ResponseBody ResponseDto<?> deleteById(@PathVariable Integer id) {

        // 세션의 id와 comment의 userId와 비교
        User principal = (User) session.getAttribute("principal");

        commentService.댓글삭제(id, principal);
        return new ResponseDto<>(1, "성공", null);
    }

}
