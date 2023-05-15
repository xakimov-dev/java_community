package uz.community.javacommunity.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public record SubArticleContentRequest(
        @NotNull(message = "error.invalid.category_id.not_null") UUID categoryId,
        @NotNull(message = "error.invalid.article_id.not_null") UUID articleId,
        @NotNull(message = "error.invalid.sub_article_id.not_null") UUID subArticleId,
        @NotBlank(message = "Content cannot not be blank") String content,
        @JsonProperty(value = "is_paragraph")@NotNull(message = "isParagraph property cannot be null value") Boolean isParagraph
){


}
