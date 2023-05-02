package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CommonSchemaValidator commonSchemaValidator;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

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

    private void throwIfCategoryExist(String name) {
        if (Boolean.TRUE.equals(categoryRepository.existsByCategoryKeyName(name))) {
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }

    public void throwIfCategoryCannotBeFound(UUID categoryId) {
        Optional<Category> category = categoryRepository.findByCategoryKeyId(categoryId);
        if (category.isEmpty()) {
            throw new RecordNotFoundException("Category with id : '" +
                    categoryId + "' cannot be found!");
        }
    }

    public List<CategoryResponse> getAllCategory() {
        List<CategoryResponse> allParentIdIsNull = getAllParentIdIsNull(categoryRepository.findAllBy());
        allParentIdIsNull.forEach(this::getContentOfCategory);
        return allParentIdIsNull;
    }
    public void getContentOfCategory(CategoryResponse categoryResponse){
        List<Article> allByArticleKeyCategoryId = articleRepository.findAllByArticleKey_CategoryId(categoryResponse.getId()).get();
        if (!allByArticleKeyCategoryId.isEmpty()) {
            categoryResponse.setArticleResponseList(allByArticleKeyCategoryId.stream().map(ArticleResponse::from).toList());
        }
        List<Category> allByParentId = categoryRepository.findAllByParentId(categoryResponse.getId()).get();
        if (!allByParentId.isEmpty()) {
            List<CategoryResponse> list = allByParentId.stream().map(CategoryResponse::from).toList();
            list.forEach(this::getContentOfCategory);
            categoryResponse.setChildCategoryResponseList(list);
        }
    }

    private  List<CategoryResponse> getAllParentIdIsNull(List<Category> categoryAll) {
        return categoryAll.stream().filter(category -> category.getParentId() == null)
                .map(CategoryResponse::from)
                .toList();
    }
}
