package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.converter.CategoryConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.Category;
import uz.community.javacommunity.controller.dto.CategoryResponse;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.CategoryRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CommonSchemaValidator commonSchemaValidator;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final CategoryConverter categoryConverter;
    private final ArticleConverter articleConverter;

    public Category saveCategory(Category category) {
        throwIfCategoryExist(category.getCategoryKey().getName());
        UUID parentId = category.getParentId();
        if (parentId != null) {
            commonSchemaValidator.validateCategory(parentId);
        }
        return categoryRepository.save(category);
    }

    private void throwIfCategoryExist(String name) {
        if (Boolean.TRUE.equals(categoryRepository.existsByCategoryKeyName(name))) {
            throw new AlreadyExistsException(String.format("Category already exist for name %s", name));
        }
    }

    public Category update(Category category){
        if (category.getParentId()!= null) {
            commonSchemaValidator.validateCategory(category.getParentId());
        }
        commonSchemaValidator.validateCategory(category.getCategoryKey().getId());
        return categoryRepository.save(category);
    }

    public List<Category> getChildListByParentId(UUID id) {
        commonSchemaValidator.validateCategory(id);
        return categoryRepository.getCategoryByParentId(id);
    }

    public List<CategoryResponse> listCategoriesWithChildArticlesAndCategories() {
        List<Category> categories = categoryRepository.findAllBy();
        List<CategoryResponse> categoryResponses = categoryConverter.convertEntitiesToResponse(categories);
        categoryResponses.forEach(this::getContentOfCategory);
        return categoryResponses;
    }
    public void getContentOfCategory(CategoryResponse categoryResponse){
        List<Article> articles = articleRepository.findAllByArticleKey_CategoryId(categoryResponse.getId());
        if (!articles.isEmpty()) {
            categoryResponse.setArticleResponseList(articleConverter.convertEntitiesToResponse(articles));
        }
        List<Category> categories = categoryRepository.findAllByParentId(categoryResponse.getId());
        if (!categories.isEmpty()) {
            List<CategoryResponse> categoryResponses = categoryConverter.convertEntitiesToResponse(categories);
            categoryResponses.forEach(this::getContentOfCategory);
            categoryResponse.setChildCategoryResponseList(categoryResponses);
        }
    }


}
