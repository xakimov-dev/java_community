package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.converter.CategoryConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
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
    private final ArticleService articleService;

    public Category saveCategory(Category category, String createdBy) {
        String categoryName = category.getName();
        UUID parentId = category.getParentId();
        commonSchemaValidator.validateCategoryExist(categoryName);
        if (parentId != null) {
            commonSchemaValidator.validateCategory(parentId);
        }
        Instant now = Instant.now();
        category.setId(UUID.randomUUID());
        category.setCreatedBy(createdBy);
        category.setCreatedDate(now);
        category.setModifiedBy(createdBy);
        category.setModifiedDate(now);
        return categoryRepository.save(category);
    }

    public Category update(Category newCategory, String updatedBy, UUID categoryId) {
        UUID parentId = newCategory.getParentId();
        if (parentId != null) {
            commonSchemaValidator.validateCategory(parentId);
        }
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new RecordNotFoundException(String.format("Category not found for id %s", categoryId)));
        commonSchemaValidator.validateCategoryExistByNameAndParentID(newCategory.getName(), parentId, categoryId);
        category.setName(newCategory.getName());
        category.setParentId(parentId);
        category.setModifiedDate(Instant.now());
        category.setModifiedBy(updatedBy);
        return categoryRepository.save(category);
    }

    public List<Category> getChildListByParentId(UUID id) {
        commonSchemaValidator.validateCategory(id);
        return categoryRepository.findAllByParentId(id);
    }

    public List<CategoryResponse> listCategoriesWithChildArticlesAndCategories() {
        List<CategoryResponse> categoryResponses = CategoryConverter.from(categoryRepository.findAllByParentId(null));
        categoryResponses.forEach(this::getContentOfCategory);
        return categoryResponses;
    }

    public void getContentOfCategory(CategoryResponse categoryResponse) {
        List<Article> articles = articleRepository.findAllByCategoryId(categoryResponse.getId());
        if (!articles.isEmpty()) {
            categoryResponse.setArticleResponseList(articles.stream().map(ArticleConverter::from).toList());
        }
        List<Category> categories = categoryRepository.findAllByParentId(categoryResponse.getId());
        if (!categories.isEmpty()) {
            List<CategoryResponse> categoryResponses = categories.stream().map(CategoryConverter::from).toList();
            categoryResponses.forEach(this::getContentOfCategory);
            categoryResponse.setChildCategoryResponseList(categoryResponses);
        }
    }

    public void delete(UUID id) {
        commonSchemaValidator.validateCategory(id);
        articleService.deleteByCategoryId(id);
        List<Category> categories = categoryRepository.findAllByParentId(id);
        if (!categories.isEmpty()) {
            categories.forEach(category -> delete(category.getId()));
        }
        categoryRepository.deleteById(id);
    }
}
