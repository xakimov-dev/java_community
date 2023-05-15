package uz.community.javacommunity.controller.converter;

import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Component
public class CategoryConverter implements Converter<Category, CategoryRequest, CategoryResponse>{
    @Override
    public Category convertRequestToEntity(CategoryRequest categoryRequest, String name, UUID id) {
        Instant now = Instant.now();
        return Category.builder()
                .categoryKey(Category.CategoryKey.of(id, categoryRequest.getName()))
                .modifiedBy(name)
                .modifiedDate(now)
                .parentId(categoryRequest.getParentId())
                .build();
    }

    @Override
    public Category convertRequestToEntity(CategoryRequest categoryRequest, String name) {
        Instant now = Instant.now();
        return Category.builder()
                .categoryKey(Category.CategoryKey.of(UUID.randomUUID(), categoryRequest.getName()))
                .createdBy(name)
                .modifiedBy(name)
                .createdDate(now)
                .modifiedDate(now)
                .parentId(categoryRequest.getParentId())
                .build();
    }

    @Override
    public CategoryResponse convertEntityToResponse(Category category) {
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

    @Override
    public List<CategoryResponse> convertEntitiesToResponse(List<Category> categories) {
        return categories.stream().map(this::convertEntityToResponse).toList();
    }
}
