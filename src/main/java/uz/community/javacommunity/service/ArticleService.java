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
    private final SubArticleContentRepository subArticleContentRepository;
    private final SubArticleConverter subArticleConverter;
    private final SubArticleContentConverter subArticleContentConverter;
    private final ArticleConverter articleConverter;
    private final CommonSchemaValidator commonSchemaValidator;

    public Article create(Article article) {
        commonSchemaValidator.validateCategory(article.getArticleKey().getCategoryId());
        throwIfArticleAlreadyExists(article.getName(), article.getArticleKey().getCategoryId());
        return articleRepository.save(article);
    }

    private void throwIfArticleAlreadyExists(String name, UUID categoryId) {
        Optional<Article> article = articleRepository
                .findByNameAndArticleKey_CategoryId(name, categoryId);
        if (article.isPresent()) {
            throw new AlreadyExistsException("Article with name : '" +
                    name + "' already exists");
        }
    }

    public Article update(Article article) {
        commonSchemaValidator.validateCategory(article.getArticleKey().getCategoryId());
        commonSchemaValidator.validateArticle(article.getArticleKey().getId());
        return articleRepository.save(article);
    }

    public ArticleResponse getArticleById(UUID id) {
        Optional<Article> optionalArticle = articleRepository.findByArticleKey_Id(id);
        if (optionalArticle.isPresent()) {
            ArticleResponse articleResponse = articleConverter.convertEntityToResponse(optionalArticle.get());
           getSubArticlesContentByArticle(articleResponse);
           return articleResponse;
        }
        return  null;
    }

    private void getSubArticlesContentByArticle(ArticleResponse articleResponse) {
        List<SubArticle> subArticles = subArticleRepository.findAllBySubArticleKey_ArticleId(articleResponse.getId());
        if (!subArticles.isEmpty()) {
            List<SubArticleResponse> subArticleResponses = subArticles.stream().map(subArticleConverter::convertEntityToResponse).toList();
            subArticleResponses.forEach(this::getSubArticlesContentBySubArticle);
            articleResponse.setSubArticleResponseList(subArticleResponses);
        }
    }


    private void getSubArticlesContentBySubArticle(SubArticleResponse subArticleResponse) {
        List<SubArticleContent> subArticleContents = subArticleContentRepository.findAllBySubArticleContentKeySubArticleId(subArticleResponse.getId());
        if (subArticleContents.isEmpty()) {
            subArticleResponse.setSubArticleContentResponses(subArticleContentConverter.convertEntitiesToResponse(subArticleContents));
        }
        List<SubArticle> subArticles = subArticleRepository.findAllBySubArticleKeyParentSubArticleId(subArticleResponse.getId());
        if (!subArticles.isEmpty()) {
            List<SubArticleResponse> subArticleResponses = subArticles.stream().map(subArticleConverter::convertEntityToResponse).toList();
            subArticleResponses.forEach(this::getSubArticlesContentBySubArticle);
            subArticleResponse.setChildSubArticleList(subArticleResponses);
        }

    }
    public List<Article> getAllByCategoryId(UUID categoryId) {
        return articleRepository.findAllByArticleKey_CategoryId(categoryId);
    }

    public void delete(UUID id) {
        Article articleByArticleKeyId = articleRepository.findByArticleKey_Id(id).orElseThrow(
                ()->new RecordNotFoundException(String.format("Article not found for id %s",id)));
        articleRepository.delete(articleByArticleKeyId);
    }

}
