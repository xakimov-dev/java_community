package uz.community.javacommunity.controller.dto;

import javax.validation.constraints.NotBlank;
import java.util.UUID;


public class CategoryCreateRequest extends BaseCategoryRequest{
    CategoryCreateRequest(@NotBlank(message = "Category name must not blank") String name, UUID parentId) {
        super(name, parentId);
    }
}
