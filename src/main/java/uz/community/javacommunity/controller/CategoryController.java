package uz.community.javacommunity.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.service.CategoryService;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.saveCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);
    }

}
