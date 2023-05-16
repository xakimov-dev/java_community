package uz.community.javacommunity.controller.converter;

import uz.community.javacommunity.controller.domain.Article;
import uz.community.javacommunity.controller.dto.ArticleRequest;
import uz.community.javacommunity.controller.dto.ArticleResponse;

import java.util.List;

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
    public static Article of(ArticleRequest articleRequest){
        return Article.builder()
                .name(articleRequest.getName())
                .build();
    }
}
