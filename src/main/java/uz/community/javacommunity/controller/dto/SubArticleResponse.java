package uz.community.javacommunity.controller.dto;

import uz.community.javacommunity.controller.domain.SubArticle;

import java.time.Instant;
import java.util.UUID;

public record SubArticleResponse(
        UUID id,
        UUID categoryId,
        UUID articleId,
        UUID parentSubArticleId,
        String name,
        String createdBy,
        Instant createdDate,
        String modifiedBy,
        Instant modifiedDate
) {
    public static SubArticleResponse of(SubArticle subArticle) {
        return new SubArticleResponse(
                subArticle.getSubArticleKey().getId(),
                subArticle.getSubArticleKey().getCategoryId(),
                subArticle.getSubArticleKey().getArticleId(),
                subArticle.getParentSubArticleId(),
                subArticle.getName(),
                subArticle.getCreatedBy(),
                subArticle.getCreatedDate(),
                subArticle.getModifiedBy(),
                subArticle.getModifiedDate()
        );
    }
}
