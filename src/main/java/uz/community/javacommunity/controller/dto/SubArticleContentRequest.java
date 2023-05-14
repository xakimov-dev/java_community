package uz.community.javacommunity.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubArticleContentRequest{
       private @NotNull(message = "error.invalid.category_id.not_null") UUID categoryId;
       private @NotNull(message = "error.invalid.article_id.not_null") UUID articleId;
       private @NotNull(message = "error.invalid.sub_article_id.not_null") UUID subArticleId;
       private @NotBlank(message = "Content cannot not be blank") String content;
       @JsonProperty(value = "is_paragraph")
       @NotNull(message = "isParagraph property cannot be null value")
       private boolean isParagraph;

}
