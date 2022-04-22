package site.metacoding.blogv2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.UserService;
import site.metacoding.blogv2.web.dto.ResponseDto;
import site.metacoding.blogv2.web.dto.UpdateDto;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    // @PostMapping("/join")
    // public ResponseDto<?> join(@RequestBody JoinReqDto joinReqDto) {
    // // System.out.println(joinDto);
    // userService.회원가입(joinReqDto);
    // return new ResponseDto<>(1, "회원가입성공", null);
    // }

    @PutMapping("/s/api/user/update/{id}")
    public ResponseDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
        System.out.println("여기까지이이지지지");
        userService.회원수정(id, updateDto);
        return new ResponseDto<>(1, "성공", null);
    }

    // 우리 웹브라우저에서는 현재 사용안함. 추후 앱에서 요청시에 사용할 예정
    @GetMapping("/s/api/user/{id}")
    public ResponseDto<?> userInfo(@PathVariable Integer id) {
        User userEntity = userService.회원정보(id);
        return new ResponseDto<>(1, "성공", userEntity);
    }

}