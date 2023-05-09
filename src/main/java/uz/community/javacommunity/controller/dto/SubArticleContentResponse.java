package uz.community.javacommunity.controller.dto;

import uz.community.javacommunity.controller.domain.SubArticleContent;

import java.time.Instant;
import java.util.UUID;

public record SubArticleContentResponse(
        UUID id,
        UUID categoryId,
        UUID articleId,
        UUID subArticleId,
        String content,
        boolean isParagraph,
        String createdBy,
        Instant createdDate,
        String modifiedBy,
        Instant modifiedDate
) {
    public static SubArticleContentResponse of(SubArticleContent subArticleContent) {
        return new SubArticleContentResponse(
                subArticleContent.getSubArticleContentKey().getId(),
                subArticleContent.getSubArticleContentKey().getCategoryId(),
                subArticleContent.getSubArticleContentKey().getArticleId(),
                subArticleContent.getSubArticleContentKey().getSubArticleId(),
                subArticleContent.getContent(),
                subArticleContent.isParagraph(),
                subArticleContent.getCreatedBy(),
                subArticleContent.getCreatedDate(),
                subArticleContent.getModifiedBy(),
                subArticleContent.getModifiedDate()
        );
    }
}
