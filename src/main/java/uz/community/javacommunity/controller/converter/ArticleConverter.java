package uz.community.javacommunity.controller.converter;

import org.springframework.stereotype.Component;
import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
public class ArticleConverter implements Converter<Article, ArticleRequest, ArticleResponse> {
    @Override
    public Article convertRequestToEntity(ArticleRequest articleRequest, String name, UUID id) {
        Instant now = Instant.now();
        return Article.builder()
                .name(articleRequest.getName())
                .articleKey(Article.ArticleKey.of(id, articleRequest.getCategoryId()))
                .modifiedBy(name)
                .modifiedDate(now)
                .build();
    }

    @Override
    public Article convertRequestToEntity(ArticleRequest articleRequest, String name) {
        Instant now = Instant.now();
        return Article.builder()
                .name(articleRequest.getName())
                .articleKey(Article.ArticleKey.of(UUID.randomUUID(), articleRequest.getCategoryId()))
                .modifiedBy(name)
                .createdBy(name)
                .createdDate(now)
                .modifiedDate(now)
                .build();
    }

    @Override
    public ArticleResponse convertEntityToResponse(Article article) {
        return ArticleResponse.builder()
                .name(article.getName())
                .id(article.getArticleKey().getId())
                .categoryId(article.getArticleKey().getCategoryId())
                .createdBy(article.getCreatedBy())
                .createdDate(article.getCreatedDate().toString())
                .build();
    }

    @Override
    public List<ArticleResponse> convertEntitiesToResponse(List<Article> articles) {
        return articles.stream().map(this::convertEntityToResponse).collect(Collectors.toList());
    }
}
