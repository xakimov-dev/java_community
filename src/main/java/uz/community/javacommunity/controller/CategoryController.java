package uz.community.javacommunity.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import uz.community.javacommunity.controller.converter.CategoryConverter;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.*;
import uz.community.javacommunity.service.CategoryService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;


import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@EnableMethodSecurity
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse saveCategory(@RequestBody @Validated CategoryRequest categoryRequest, Principal principal) {
        return CategoryConverter.from(categoryService.saveCategory(CategoryConverter.of(categoryRequest), principal.getName()));
    }
    @GetMapping("/child/{id}")
    public List<CategoryResponse>getChildList(
            @PathVariable UUID id
            ){
        return  CategoryConverter.getChildList(categoryService.getChildListByParentId(id));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategory(){
        return categoryService.listCategoriesWithChildArticlesAndCategories();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(OK)
    @Operation(summary = "Update category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse update(
            @RequestBody @Validated CategoryRequest categoryRequest,
            @PathVariable UUID id,
            Principal principal
    ) {
        return CategoryConverter.from(categoryService.update(id, CategoryConverter.of(categoryRequest), principal.getName()));
    }

}
