package uz.community.javacommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.community.javacommunity.controller.domain.User;
import uz.community.javacommunity.controller.dto.CategoryDTO;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryUpdateRequestDTO;
import uz.community.javacommunity.service.CategoryService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping
    public CategoryDTO saveCategory(
            @RequestBody CategoryRequest categoryDTO,
            Principal principal
            ){
        return categoryService.saveCategory(categoryDTO,principal.getName());
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(
            @RequestParam String id,
            @RequestBody CategoryUpdateRequestDTO categoryDTO,
            Principal principal
            ){
        return categoryService.updateCategory(categoryDTO,UUID.fromString(id),principal.getName());
    }

}
