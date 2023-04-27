package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.domain.keys.CategoryKey;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.repository.CategoryRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        final String categoryName = categoryRequest.getName();
        Category category = CategoryRequest.of(categoryRequest);

        checkCategoryByNameAndThrowIfExist(categoryName);
        if (categoryRequest.getParentId() == null) {
            Category savedCategory = categoryRepository.save(category);
            return CategoryResponse.of(savedCategory);
        }
        UUID parentId = categoryRequest.getParentId();

        final String PARENT_ID_NOT_FOUND = "Category with id [%s] not found".formatted(parentId);

        categoryRepository
                .findByCategoryKey_Id(parentId)
                .ifPresentOrElse((c) -> categoryRepository.save(category),
                        () ->{throw new RecordNotFoundException(PARENT_ID_NOT_FOUND);});

        return CategoryResponse.of(category);
    }

    private void checkCategoryByNameAndThrowIfExist(String categoryName) {
        String MESSAGE = "Category with name [%s] already exists".formatted(categoryName);

        categoryRepository.findByCategoryKey_Name(categoryName).ifPresent((c) -> {
            throw new AlreadyExistsException(MESSAGE);
        });
    }
}
