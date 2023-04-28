package uz.community.javacommunity.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import uz.community.javacommunity.controller.domain.Article;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class ArticleResponse {
    Article.ArticleKey articleKey;
    String name;
    String createdBy;
    Instant createdDate;
    String modifiedBy;
    Instant modifiedDate;

    public static ArticleResponse from(Article article){
        return ArticleResponse.builder()
                .articleKey(article.getArticleKey())
                .name(article.getName())
                .createdBy(article.getCreatedBy())
                .createdDate(article.getCreatedDate())
                .modifiedBy(article.getModifiedBy())
                .modifiedDate(article.getModifiedDate())
                .build();
    }
}
