package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.community.javacommunity.controller.domain.Article;

@Getter
@Setter
@Builder
public class ArticleResponse {
    private String name;
    private String articleId;
    private String createdBy;
    private String createdDate;
    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .name(article.getName())
                .articleId(article.getArticleKey().getId().toString())
                .createdBy(article.getCreatedBy())
                .createdDate(article.getCreatedDate().toString())
                .build();
    }
}
