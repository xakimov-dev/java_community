package uz.community.javacommunity.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class BaseSubArticleContentRequest {
    @NotNull(message = "error.invalid.sub_article_id.not_null")
    UUID subArticleId;
    @NotBlank(message = "Content cannot not be blank")
    String content;
    @NotNull(message = "isParagraph property cannot be null value")
    Boolean isParagraph;
}
