package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.converter.CategoryConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.CategoryRequest;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CommonSchemaValidator commonSchemaValidator;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public Category saveCategory(Category category, String createdBy) {

        String categoryName = category.getCategoryKey().getName();
        UUID parentId = category.getParentId();
        throwIfCategoryExist(categoryName);

        if (parentId != null) {
            commonSchemaValidator.validateCategory(parentId);
        }

        Instant now = Instant.now();
        category.setCategoryKey(Category.CategoryKey.of(UUID.randomUUID(),categoryName));
        category.setCreatedBy(createdBy);
        category.setCreatedDate(now);
        category.setModifiedBy(createdBy);
        category.setModifiedDate(now);

        return categoryRepository.save(category);
    }

    private void throwIfCategoryExist(String name) {
        if (Boolean.TRUE.equals(categoryRepository.existsByCategoryKeyName(name))) {
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }

    public Category update(UUID id, Category newCategory, String updatedBy){
        UUID parentId = newCategory.getParentId();

        if (parentId!= null) {
            commonSchemaValidator.validateCategory(parentId);
        }

        Category category = categoryRepository.findByCategoryKeyId(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Category not found for id %s", id)));

        category.setCategoryKey(Category.CategoryKey.of(id,newCategory.getCategoryKey().getName()));
        category.setModifiedDate(Instant.now());
        category.setModifiedBy(updatedBy);

        return categoryRepository.save(category);
    }

    public List<Category> getChildListByParentId(UUID id) {
        return categoryRepository.getCategoryByParentId(id);
    }

    public void throwIfCategoryCannotBeFound(UUID categoryId) {
        Optional<Category> category = categoryRepository.findByCategoryKeyId(categoryId);
        if (category.isEmpty()) {
            throw new RecordNotFoundException("Category with id : '" +
                    categoryId + "' cannot be found!");
        }
    }

    public List<CategoryResponse> listCategoriesWithChildArticlesAndCategories() {
        List<CategoryResponse> allParentIdIsNull = getAllParentIdIsNull(categoryRepository.findAllBy());
        allParentIdIsNull.forEach(this::getContentOfCategory);
        return allParentIdIsNull;
    }
    public void getContentOfCategory(CategoryResponse categoryResponse){
        List<Article> allByArticleKeyCategoryId = articleRepository.findAllByArticleKey_CategoryId(categoryResponse.getId());
        if (!allByArticleKeyCategoryId.isEmpty()) {
            categoryResponse.setArticleResponseList(allByArticleKeyCategoryId.stream().map(ArticleConverter::from).toList());
        }
        List<Category> allByParentId = categoryRepository.findAllByParentId(categoryResponse.getId());
        if (!allByParentId.isEmpty()) {
            List<CategoryResponse> list = allByParentId.stream().map(CategoryConverter::from).toList();
            list.forEach(this::getContentOfCategory);
            categoryResponse.setChildCategoryResponseList(list);
        }
    }

    private  List<CategoryResponse> getAllParentIdIsNull(List<Category> categoryAll) {
        return categoryAll.stream().filter(category -> category.getParentId() == null)
                .map(CategoryConverter::from)
                .toList();
    }
}
