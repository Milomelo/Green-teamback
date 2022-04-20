package site.metacoding.blogv2.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.service.PostService;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @GetMapping("/api/list")
    public ResponseEntity<?> list(String keyword, Integer page,
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> posts = postService.글목록보기(keyword, pageable);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/{userId}/list")
    public ResponseEntity<?> mylist(@PathVariable Integer userId, String mykeyword, Integer page,
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> posts = postService.유저글목록보기(userId, mykeyword, pageable);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/api/{userId}/otherlist")
    public ResponseEntity<?> otherlist(@PathVariable Integer userId, String mykeyword, Integer page,
            @PageableDefault(size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Post> posts = postService.다른유저글목록보기(userId, mykeyword, pageable);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
