package site.metacoding.blogv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.category.Category;
import site.metacoding.blogv2.domain.category.CategoryRepository;
import site.metacoding.blogv2.domain.post.Post;
import site.metacoding.blogv2.domain.post.PostRepository;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.web.Dto.PostRespDto;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void 글쓰기(Post post, User principal) {
        post.setUser(principal); // User FK 추가!!
        postRepository.save(post);
    }

    public Page<Post> 글목록보기(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword, pageable);
    }

    public PostRespDto 게시글목록보기(int userId) {
        List<Post> postsEntity = postRepository.findByUserId(userId);
        List<Category> categorysEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(postsEntity, categorysEntity);
        return postRespDto;
    }

    public Post 글상세보기(Integer id) {
        Optional<Post> postOp = postRepository.findById(id);

        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            return postEntity;
        } else {
            return null;
        }
    }

    @Transactional
    public void 글삭제하기(Integer id) {
        postRepository.deleteById(id); // 실패했을 때 내부적으로 exception 터짐
    }

    @Transactional
    public void 글수정하기(Post post, Integer id) {
        Optional<Post> postOp = postRepository.findById(id);

        if (postOp.isPresent()) {
            Post postEntity = postOp.get();
            postEntity.setTitle(post.getTitle());
            postEntity.setContent(post.getContent());
        }
    }

    public Page<Post> 유저글목록보기(Integer userId, String mykeyword, Pageable pageable) {
        return postRepository.mfindByUserID(userId, mykeyword, pageable);
    }

}
