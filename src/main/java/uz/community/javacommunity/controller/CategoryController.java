package uz.community.javacommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.service.CategoryService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse saveCategory(@RequestBody @Validated CategoryRequest categoryRequest, Principal principal) {
        Category category = categoryService.saveCategory(categoryRequest, principal.getName());
        return CategoryResponse.from(category);
    }

    @GetMapping("/get-all-parent")
    public List<CategoryResponse> getAllParent2(){
        return categoryService.getAllParent();
    }

}
