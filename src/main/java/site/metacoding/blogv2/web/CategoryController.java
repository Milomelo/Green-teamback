package site.metacoding.blogv2.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.blogv2.domain.category.Category;
import site.metacoding.blogv2.domain.user.User;
import site.metacoding.blogv2.service.CategoryService;
import site.metacoding.blogv2.web.dto.CategoryWriteReqDto;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final HttpSession session;
    private final CategoryService categoryService;

    @GetMapping("/s/category/writeForm")
    public String writeForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public String write(CategoryWriteReqDto categoryWriteReqDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            // 이부분에서 data리턴인지 html 리턴인지 이것만 구분해서 터트려줘!!
            return null;
        }

        User principal = (User) session.getAttribute("principal");
        // User principal = loginUser.getUser();
        // LoginUser loginU = (LoginUser)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // User principal = loginU.getUser();

        Category category = categoryWriteReqDto.toEntity(principal);

        // 서비스에 기능 호출 (Category 모델)
        categoryService.카테고리등록(category);

        return "redirect:/s/category/writeForm";
    }
}