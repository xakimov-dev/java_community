package uz.community.javacommunity.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record SubArticleRequest(
        @NotNull(message = "error.invalid.category_id.not_null") UUID categoryId,
        @NotNull(message = "error.invalid.article_id.not_null") UUID articleId,
        UUID parentSubArticleId,
        @NotBlank(message = "error.invalid.name.not_blank") String name) {
}
