package site.metacoding.blogv2.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.metacoding.blogv2.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinReqDto {

    private String username;

    private String password;

    private String email;
    private String blogname;

    public User toEntity() {
        User user = new User();// null 없이 넣을것만 넣으면 된다.
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setBlogname(blogname);
        return user;
    }
}