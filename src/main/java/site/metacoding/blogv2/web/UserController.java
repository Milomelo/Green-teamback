package site.metacoding.blogv2.web;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.domain.user.UserRepository;
import site.metacoding.blogv2.service.PostService;
import site.metacoding.blogv2.service.UserService;
import site.metacoding.blogv2.web.Dto.ImgDto;
import site.metacoding.blogv2.web.Dto.PostRespDto;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final HttpSession session;
    private final PostService postService;

    // 메인
    @GetMapping("/")
    public String mainForm() {
        return "/post/list";
    }

    // 로그인
    @GetMapping("/login-form")
    public String loginForm(HttpServletRequest request, Model model) {

        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();

            for (Cookie cookie : cookies) {
                System.out.println("쿠키값 : " + cookie.getName());
                if (cookie.getName().equals("remember")) {
                    model.addAttribute("remember", cookie.getValue());
                }

            }
        }

        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(User user, HttpServletResponse response) {
        User userEntity = userService.로그인(user);

        if (userEntity != null) {
            session.setAttribute("principal", userEntity);
            if (user.getRemember() != null && user.getRemember().equals("on")) {
                response.addHeader("Set-Cookie", "remember=" + user.getUsername());
            }
            return "redirect:/";
        } else {
            return "redirect:/login-form";
        }

    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/login-form"; // PostController 만들고 수정하자.
    }

    // 회원가입
    @GetMapping("/join-form")
    public String joinForm() {

        return "/user/joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {

        // 필터의 역할
        // 1. username, password, email 1.null체크, 2.공백체크
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return "redirect:/join-form";
        }
        if (user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("")) {
            return "redirect:/join-form";
        }

        userService.회원가입(user);

        return "redirect:/login-form"; // 로그인페이지 이동해주는 컨트롤러 메서드를 재활용
    }

    @GetMapping("/s/user/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        User userEntity = userService.회원정보(id);
        model.addAttribute("user", userEntity);
        System.out.println(userEntity);
        return "/user/updateForm";
    }

    // 이미지업로드
    @PostMapping("/upload")
    // 버퍼로 파싱하는게 아니라 폼태그로 전송해서 바로 읽을 수 있다.
    public String upload(ImgDto imgDto) { // 버퍼로 읽는거 1. json 2. 있는 그대로 받고 싶을 때

        UUID uuid = UUID.randomUUID();

        String requestFileName = imgDto.getFile().getOriginalFilename();
        System.out.println("전송받은 파일명 : " + requestFileName);

        String imgurl = uuid + "_" + requestFileName;
        try {
            Path filePath = Paths.get("src/main/resources/static/upload/" + imgurl);
            Files.write(filePath, imgDto.getFile().getBytes());

            userRepository.save(imgDto.toEntity(imgurl)); // DB에 들어가는건 경로가 된다.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "/updateForm"; // ViewResolver 발동시키기 위해서는 Controller (파일리턴)
    }

    // 내 블로그 연결
    @GetMapping("/s/user/my-blog/{userId}")
    public String myBlog(@PathVariable Integer userId, Model model) {
        User userEntity = userService.회원정보(userId);
        PostRespDto postRespDto = postService.게시글목록보기(userId);
        model.addAttribute("postRespDto", postRespDto);
        model.addAttribute("user", userEntity);
        return "/user/myBlog";
    }

    // 카테고리
    @GetMapping("/user/{id}/post")
    public String postList(@PathVariable Integer id, Model model) {

        // SELECT * FROM category WHERE userId = :id
        // 카테고리 가져가세요!!
        PostRespDto postRespDto = postService.게시글목록보기(id);
        model.addAttribute("postRespDto", postRespDto);
        System.out.println(postRespDto);
        System.out.println("=========postlist입니다=======================");
        return "/post/list";
    }

    // 일반 블로그 연결
    @GetMapping("/user/other-blog/{userId}")
    public String otherBlog(@PathVariable Integer userId, Model model) {
        User userEntity = userService.회원아이디불러오기(userId);
        model.addAttribute("user", userEntity);
        return "/user/otherBlog";
    }
}
