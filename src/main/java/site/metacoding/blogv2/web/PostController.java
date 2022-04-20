package site.metacoding.blogv2.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.category.Category;
import site.metacoding.blogv2.domain.category.CategoryRepository;
import site.metacoding.blogv2.domain.comment.Comment;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.PostService;

import site.metacoding.blogv2.web.dto.CommentResponseDto;
import site.metacoding.blogv2.web.dto.ResponseDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final CategoryRepository categoryRepository;
    private final HttpSession session;
    private final PostService postService;

    @GetMapping("/")
    public String mainForm(Model model) {

        return "/post/list";
    }

    // 글쓰기
    @GetMapping("/s/post/{id}/write-form")
    public String postForm(Model model, @PathVariable Integer id) {

        User pri = (User) session.getAttribute("principal");
        List<Category> categorys = categoryRepository.findByUserId(id);
        System.out.println("================카테고리입니다" + categorys);
        if (pri.getId() == id) {

            // for (int i = 0; i < categorys.size(); i++) {

            // System.out.println(categorys.get(i).getTitle());

            // }

            model.addAttribute("categorys", categorys);
        }

        return "/post/writeForm";
    }

    @PostMapping("/s/post")
    public String write(Post post) {

        if (session.getAttribute("principal") == null) {
            return "redirect:/login-form";
        }
        if (post.getSecret() == null) {
            post.setSecret("0");

        }

        User principal = (User) session.getAttribute("principal");
        postService.글쓰기(post, principal);
        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String Detail(@PathVariable Integer id, Model model) {

        User principal = (User) session.getAttribute("principal");

        Post postEntity = postService.글상세보기(id);

        String rawContent = postEntity.getContent();
        String encContent = rawContent
                .replaceAll("<script>", "&lt;script&gt;")
                .replaceAll("</script>", "&lt;script/&gt;");
        postEntity.setContent(encContent);

        List<CommentResponseDto> comments = new ArrayList<>();

        for (Comment comment : postEntity.getComments()) {
            CommentResponseDto dto = new CommentResponseDto();
            dto.setComment(comment);

            if (principal != null) {

                if (principal.getId() == comment.getUser().getId()) {
                    dto.setAuth(true); // or false
                } else {
                    dto.setAuth(false); // or false
                }
            } else {
                dto.setAuth(false); // or false
            }

            comments.add(dto);
        }
        if (principal != null) {

            if (principal.getId() == postEntity.getUser().getId()) {

                if (postEntity.getSecret().equals("1")) {

                    model.addAttribute("secret", false);

                } else {

                    model.addAttribute("all", true);

                }

            }

        } else {

            if (postEntity.getSecret().equals("1")) {

                model.addAttribute("secret", true);

            } else {

                model.addAttribute("all", true);

            }

        }

        model.addAttribute("comments", comments);
        model.addAttribute("post", postEntity);

        return "/post/detail";
    }

    @DeleteMapping("/s/post/{id}")
    public @ResponseBody ResponseDto<String> delete(@PathVariable Integer id) {

        User principal = (User) session.getAttribute("principal");

        if (principal == null) { // 로그인이 안됐다는 뜻
            return new ResponseDto<String>(-1, "로그인이 되지 않았습니다", null);
        }

        Post postEntity = postService.글상세보기(id);

        if (principal.getId() != postEntity.getUser().getId()) { // 권한이 없다는 뜻
            return new ResponseDto<String>(-1, "해당 글을 삭제할 권한이 없습니다.", null);
        }

        postService.글삭제하기(id); // 내부적으로 exception이 터지면 무조건 스택 트레이스를 리턴한다.

        return new ResponseDto<String>(1, "성공", null);
    }

    @GetMapping("/s/post/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, Model model) {

        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "error/page1";
        }

        Post postEntity = postService.글상세보기(id);

        if (postEntity.getUser().getId() != principal.getId()) {
            return "error/page1";
        }

        model.addAttribute("post", postEntity);

        return "post/updateForm"; // ViewResolver 도움 받음.
    }

    @PutMapping("/s/post/{id}")
    public @ResponseBody ResponseDto<String> update(@PathVariable Integer id, @RequestBody Post post) {

        // 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return new ResponseDto<String>(-1, "로그인 되지 않았습니다.", null);
        }

        // 권한
        Post postEntity = postService.글상세보기(id);

        if (postEntity.getUser().getId() != principal.getId()) {
            return new ResponseDto<String>(-1, "해당 게시글을 수정할 권한이 없습니다.", null);
        }

        postService.글수정하기(post, id);

        return new ResponseDto<String>(1, "수정 성공", null);
    }

}