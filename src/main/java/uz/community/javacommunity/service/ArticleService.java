package uz.community.javacommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.community.javacommunity.common.exception.AlreadyExistsException;
import uz.community.javacommunity.common.exception.RecordNotFoundException;
import uz.community.javacommunity.controller.converter.ArticleConverter;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.domain.SubArticle;
import uz.community.javacommunity.controller.dto.ArticleResponse;
import uz.community.javacommunity.controller.dto.SubArticleResponse;
import uz.community.javacommunity.controller.repository.ArticleRepository;
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

    public Article create(Article article, String currentUser) {
        UUID categoryId = article.getArticleKey().getCategoryId();
        commonSchemaValidator.validateCategory(categoryId);
        throwIfArticleAlreadyExists(article.getName(), categoryId);

        Instant now = Instant.now();
        article.setArticleKey(Article.ArticleKey.of(UUID.randomUUID(),categoryId));
        article.setCreatedBy(currentUser);
        article.setCreatedDate(now);
        article.setModifiedBy(currentUser);
        article.setModifiedDate(now);

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

    public Article update(UUID id, Article newArticle, String username) {
        UUID categoryId = newArticle.getArticleKey().getCategoryId();
        commonSchemaValidator.validateCategory(categoryId);
        Article article = articleRepository.findArticleByArticleKeyId(id).orElseThrow(
                () -> new RecordNotFoundException(String.format("Article not found for id %s", id)));
        article.setArticleKey(Article.ArticleKey.of(id,categoryId));
        article.setModifiedBy(username);
        article.setModifiedDate(Instant.now());

        return articleRepository.save(article);
    }

    public ArticleResponse getArticleById(UUID id) {
        Optional<Article> optionalArticle = articleRepository.findByArticleKey_Id(id);
        if (optionalArticle.isPresent()) {
            ArticleResponse articleResponse = ArticleConverter.from(optionalArticle.get());
           getSubArticlesContentByArticle(articleResponse);
           return articleResponse;
        }
        return  null;
    }

    private void getSubArticlesContentByArticle(ArticleResponse article) {
        List<SubArticle> subArticles = subArticleRepository.findAllBySubArticleKey_ArticleId(article.getArticleId());
        if (!subArticles.isEmpty()) {
            List<SubArticleResponse> list = subArticles.stream().map(SubArticleResponse::of).toList();
            list.forEach(this::getSubArticlesContentBySubArticle);
            article.setSubArticleResponseList(list);
        }
    }


    private void getSubArticlesContentBySubArticle(SubArticleResponse subArticleResponse) {
        List<SubArticle> subArticles = subArticleRepository.findAllByParentSubArticleId(subArticleResponse.getId());
        if (!subArticles.isEmpty()) {
            List<SubArticleResponse> list = subArticles.stream().map(SubArticleResponse::of).toList();
            list.forEach(this::getSubArticlesContentBySubArticle);
            subArticleResponse.setChildSubArticleList(list);
        }

    }
    public List<Article> getAllByCategoryId(UUID categoryId) {
        return articleRepository.findAllByArticleKey_CategoryId(categoryId);
    }

    public void delete(UUID id) {
        Article articleByArticleKeyId = articleRepository.findArticleByArticleKeyId(id).orElseThrow(
                ()->new RecordNotFoundException(String.format("Article not found for id %s",id)));
        articleRepository.delete(articleByArticleKeyId);
    }

}
