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
public class UpdateDto {
    private String blogname;
    private String password;
    private String email;
    private String blogtitle;
    @NonNull
    private MultipartFile profilefFile;

    public User toEntity(String profile) {
        User user = new User();
        user.setBlogtitle(blogtitle);
        user.setEmail(email);
        user.setPassword(password);
        user.setBlogname(blogname);
        user.setProfile(profile);
        return user;
    }
}