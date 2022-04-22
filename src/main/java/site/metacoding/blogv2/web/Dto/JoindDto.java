package site.metacoding.blogv2.web.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import site.metacoding.blogv2.domain.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoindDto {
    @NonNull
    private String username;
    private String blogtitle;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String blogname;
    @NonNull
    private MultipartFile profilefFile;

    public User toEntity(String profile) {
        User user = new User();
        user.setUsername(username);
        user.setBlogtitle(blogtitle);
        user.setEmail(email);
        user.setPassword(password);
        user.setBlogname(blogname);
        user.setProfile(profile);
        return user;
    }

}
