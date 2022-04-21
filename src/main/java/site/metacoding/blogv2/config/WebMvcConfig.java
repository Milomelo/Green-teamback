package site.metacoding.blogv2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web.xml 설정파일 ->POJO

    @Value("${file.path}") // 롬복 밸류 말고!
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/upload/**") // 슬래시로 찾게된다면 슬래시 업로드에 접근한다.
                .addResourceLocations("file:///" + uploadFolder) // file 찾을때는 :/// 사용 - 한번다운받으면 캐싱한다 한시간마다
                .setCachePeriod(60 * 10 * 6) // 초단위 = 한시간
                .resourceChain(true) // 캐싱한 파일을 사용할 수 있냐 없냐 false면 서버 다시 찾는다!
                .addResolver(new PathResourceResolver());
    }

}