package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.community.javacommunity.controller.domain.Article;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ArticleResponse {
    private String name;
    private UUID categoryId;
    private UUID articleId;
    private String createdBy;
    private String createdDate;
    private List<SubArticleResponse> subArticleResponseList;
    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .name(article.getName())
                .articleId(article.getArticleKey().getId())
                .categoryId(article.getArticleKey().getCategoryId())
                .createdBy(article.getCreatedBy())
                .createdDate(article.getCreatedDate().toString())
                .build();
    }
    public static List<ArticleResponse> fromList(List<Article> articles) {
        return articles.stream().map(ArticleResponse::from).toList();
    }
}
