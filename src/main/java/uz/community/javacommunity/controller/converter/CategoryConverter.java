package uz.community.javacommunity.controller.converter;

import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryCreateRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.dto.CategoryUpdateRequest;

import java.util.List;
import java.util.UUID;

public class CategoryConverter {
    public static CategoryResponse from(Category category) {
        return CategoryResponse
                .builder()
                .id(category.getCategoryKey().getId())
                .name(category.getCategoryKey().getName())
                .createdBy(category.getCreatedBy())
                .createdDate(category.getCreatedDate())
                .modifiedDate(category.getModifiedDate())
                .modifiedBy(category.getModifiedBy())
                .parentId(category.getParentId())
                .build();
    }
    public static List<CategoryResponse> getChildList(List<Category> categoryList){
        return categoryList.stream().map(CategoryConverter::from).toList();
    }
    public static Category convertToEntity(CategoryCreateRequest categoryCreateRequest){
        return Category.builder()
                .categoryKey(Category.CategoryKey.of(UUID.randomUUID(), categoryCreateRequest.getName()))
                .parentId(categoryCreateRequest.getParentId())
                .build();
    }

    public static Category convertToEntity(UUID id, CategoryUpdateRequest categoryUpdateRequest){
        return Category.builder()
                .categoryKey(Category.CategoryKey.of(id, categoryUpdateRequest.getName()))
                .parentId(categoryUpdateRequest.getParentId())
                .build();
    }

}
