package uz.community.javacommunity.controller.converter;

import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleCreateRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;

import java.util.List;
import java.util.UUID;

public class ArticleConverter {
    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getArticleKey().getId())
                .name(article.getName())
                .articleId(article.getArticleKey().getId())
                .categoryId(article.getArticleKey().getCategoryId())
                .createdBy(article.getCreatedBy())
                .createdDate(article.getCreatedDate().toString())
                .build();
    }
    public static List<ArticleResponse> fromList(List<Article> articles) {
        return articles.stream().map(ArticleConverter::from).toList();
    }
    public static Article convertToEntity(ArticleCreateRequest articleCreateRequest){
        return Article.builder()
                .articleKey(Article.ArticleKey.of(UUID.randomUUID(), articleCreateRequest.getCategoryId()))
                .name(articleCreateRequest.getName())
                .build();
    }

    public static Article convertToEntity(UUID id,ArticleCreateRequest articleCreateRequest){
        return Article.builder()
                .articleKey(Article.ArticleKey.of(id, articleCreateRequest.getCategoryId()))
                .name(articleCreateRequest.getName())
                .build();
    }
}
