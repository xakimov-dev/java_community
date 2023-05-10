package uz.community.javacommunity.controller.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record SubArticleContentRequest(
        @NotNull(message = "error.invalid.category_id.not_null") UUID categoryId,
        @NotNull(message = "error.invalid.article_id.not_null") UUID articleId,
        @NotNull(message = "error.invalid.sub_article_id.not_null") UUID subArticleId,
        String content,
        boolean isParagraph
) {
}
