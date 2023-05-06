package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CommonSchemaValidator commonSchemaValidator;
    private final CategoryRepository categoryRepository;

    public Category saveCategory(CategoryRequest categoryRequest, String createdBy) {

        String categoryName = categoryRequest.getName();
        UUID parentId = categoryRequest.getParentId();
        throwIfCategoryExist(categoryName);

        if (parentId != null) {
            commonSchemaValidator.validateCategory(parentId);
        }
        Instant now = Instant.now();
        Category category = Category.builder()
                .parentId(parentId)
                .categoryKey(Category.CategoryKey.of(UUID.randomUUID(), categoryName))
                .createdBy(createdBy)
                .modifiedBy(createdBy)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        return categoryRepository.save(category);
    }

    private void throwIfCategoryExist(String name){
        if (Boolean.TRUE.equals(categoryRepository.existsByCategoryKeyName(name))){
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }


    public List<CategoryResponse> getChildListByParentId(UUID id) {
        List<Category> childList = categoryRepository.getCategoryByParentId(id);
     return childList.stream().map(CategoryResponse::from).toList();
    }
}
