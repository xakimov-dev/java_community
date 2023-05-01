package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import static uz.community.javacommunity.common.constants.ExceptionMessageConstants.*;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse saveCategory(CategoryRequest categoryRequest, String createdBy) {

        final String categoryName = categoryRequest.getName();
        final UUID parentId = categoryRequest.getParentId();
        checkCategoryByNameAndThrowIfExist(categoryName);

        Category category = CategoryRequest.from(categoryRequest, createdBy);
        if (parentId == null) {
            return saveParentCategory(category);
        }

        categoryRepository
                .findByCategoryKey_Id(parentId)
                .ifPresentOrElse((c) -> categoryRepository.save(category),
                        () -> {
                            throw new RecordNotFoundException(String.format(CATEGORY_WITH_ID_NOT_FOUND, parentId));
                        });

        return CategoryResponse.of(category);
    }

    private void checkCategoryByNameAndThrowIfExist(String categoryName) {

        categoryRepository.findByCategoryKey_Name(categoryName).ifPresent((c) -> {
            throw new AlreadyExistsException(String.format(CATEGORY_WITH_NAME_ALREADY_EXIST, categoryName));
        });
    }

    private CategoryResponse saveParentCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.of(savedCategory);

    }
}
