package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.converter.SubArticleContentConverter;
import uz.community.javacommunity.controller.converter.SubArticleConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.domain.SubArticleContent;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.SubArticleResponse;
import uz.community.javacommunity.controller.repository.ArticleRepository;
import uz.community.javacommunity.controller.repository.SubArticleContentRepository;
import uz.community.javacommunity.controller.repository.SubArticleRepository;
import uz.community.javacommunity.validation.CommonSchemaValidator;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final SubArticleRepository subArticleRepository;
    private final CommonSchemaValidator commonSchemaValidator;
    private final SubArticleContentRepository subArticleContentRepository;

    public Article create(Article article, String currentUser) {
        UUID categoryId = article.getCategoryId();
        commonSchemaValidator.validateCategory(categoryId);
        throwIfArticleAlreadyExists(article.getName(), categoryId);
        Instant now = Instant.now();
        article.setId(UUID.randomUUID());
        article.setModifiedBy(currentUser);
        article.setModifiedDate(now);
        article.setCreatedBy(currentUser);
        article.setCreatedDate(now);
        return articleRepository.save(article);
    }

    public Article update(Article newArticle, String username, UUID id) {
        UUID categoryId = newArticle.getCategoryId();
        commonSchemaValidator.validateCategory(categoryId);
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Article not found for id %s", id)));
        throwIfArticleAlreadyExists(newArticle.getName(), categoryId, id);
        article.setName(newArticle.getName());
        article.setCategoryId(categoryId);
        article.setModifiedBy(username);
        article.setModifiedDate(Instant.now());
        return articleRepository.save(article);
    }

    public ArticleResponse getArticleById(UUID id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isPresent()) {
            ArticleResponse articleResponse = ArticleConverter.from(optionalArticle.get());
            getSubArticlesContentByArticle(articleResponse);
            return articleResponse;
        }
        return null;
    }

    private void getSubArticlesContentByArticle(ArticleResponse article) {
        List<SubArticle> subArticles = subArticleRepository.findAllByArticleId(article.getId());
        if (!subArticles.isEmpty()) {
            List<SubArticleResponse> subArticleResponses = subArticles.stream().map(SubArticleConverter::from).toList();
            subArticleResponses.forEach(this::getSubArticlesContentBySubArticle);
            article.setSubArticleResponseList(subArticleResponses);
        }
    }


    private void getSubArticlesContentBySubArticle(SubArticleResponse subArticleResponse) {

        List<SubArticleContent> subArticleContents = subArticleContentRepository.findAllBySubArticleId(subArticleResponse.getId());

        if (!subArticleContents.isEmpty()) {
            subArticleResponse.setSubArticleContentResponses(SubArticleContentConverter.from(subArticleContents));
        }

        List<SubArticle> subArticles = subArticleRepository.findAllByParentSubArticleId(subArticleResponse.getId());
        if (!subArticles.isEmpty()) {
            List<SubArticleResponse> list = subArticles.stream().map(SubArticleConverter::from).toList();
            list.forEach(this::getSubArticlesContentBySubArticle);
            subArticleResponse.setChildSubArticleList(list);
        }

    }

    public List<Article> getAllByCategoryId(UUID categoryId) {
        return articleRepository.findAllByCategoryId(categoryId);
    }

    public void delete(UUID id) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Article not found for id %s", id)));
        articleRepository.delete(article);
    }

    private void throwIfArticleAlreadyExists(String name, UUID categoryId) {
        if (articleRepository.existsByNameAndCategoryId(name, categoryId)) {
            throw new AlreadyExistsException(String.format("article with name %s already exist in this category", name));
        }
    }

    private void throwIfArticleAlreadyExists(String name, UUID categoryId, UUID id) {
        if (articleRepository.existsByNameAndCategoryIdAndIdNot(name, categoryId, id)) {
            throw new AlreadyExistsException(String.format("article with name %s already exist in this category", name));
        }
    }
}
