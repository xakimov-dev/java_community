package uz.community.javacommunity.controller.converter;

import lombok.experimental.UtilityClass;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryCreateRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.CategoryUpdateRequest;

import java.util.List;

@UtilityClass
public class CategoryConverter {
    public static CategoryResponse from(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .createdBy(category.getCreatedBy())
                .createdDate(category.getCreatedDate())
                .modifiedDate(category.getModifiedDate())
                .modifiedBy(category.getModifiedBy())
                .parentId(category.getParentId())
                .build();
    }
    public static List<CategoryResponse> from(List<Category> categoryList){
        return categoryList.stream().map(CategoryConverter::from).toList();
    }
    public static Category convertToEntity(CategoryCreateRequest categoryCreateRequest){
        return Category.builder()
                .name(categoryCreateRequest.getName())
                .parentId(categoryCreateRequest.getParentId())
                .build();
    }

    public static Category convertToEntity(CategoryUpdateRequest categoryUpdateRequest){
        return Category.builder()
                .name(categoryUpdateRequest.getName())
                .parentId(categoryUpdateRequest.getParentId())
                .build();
    }
}
